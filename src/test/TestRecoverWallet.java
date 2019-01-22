import junit.framework.TestCase;
import oracle.security.pki.OracleSecretStoreTextUI;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.security.Permission;

public class TestRecoverWallet extends TestCase {
    private void deleteWallet(String path) {
        File walletDir = new File(path);
        if (walletDir.exists()) {
            String[] files = walletDir.list();
            if (files != null) {
                for (String file : files) {
                    boolean deleted = new File(walletDir.getPath(), file).delete();
                    if (!deleted) {
                        System.out.println("Warning: unable to delete "+file);
                    }
                }
            }
        }

    }

    public void testRecoverWallet() {
        // avoid exit
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkExit(int status) {
                if ("oracle.security.pki.OracleSecretStoreTextUI".equals(new Exception().getStackTrace()[3].getClassName())) {
                    throw new SecurityException();
                }
            }

            @Override
            public void checkPermission(Permission perm) {
                // Allow other activities by default
            }
        });

        String walletName = "mywallet";
        String dbInfo = "oracleserver:1521";
        String dbUser = "dbuser";
        String dbPassword = "myv3rys3cre3tp@ss";
        String walletPassword = "walletP@ssw3rd";

        String recoveredWalletName = "recoveredwallet";
        String recoverPassword = "N3wP@ssw0rd";

        System.out.println("* Clear wallet if exists");
        deleteWallet(walletName);
        deleteWallet(recoveredWalletName);

        System.out.println("* Create a new wallet with one credential entry");
        System.out.println("java -classpath lib/* oracle.security.pki.OracleSecretStoreTextUI -wrl "+walletName+" -create -createCredential "+dbInfo+" "+dbUser+" "+dbPassword);

        //-wrl mywallet -create -createCredential key username password
        System.setIn(new ByteArrayInputStream((walletPassword + "\n" + walletPassword + "\n").getBytes()));
        try {
            OracleSecretStoreTextUI.main(new String[]{"-wrl", walletName, "-create", "-createCredential", dbInfo, dbUser, dbPassword});
        } catch (SecurityException e) {
            // ignore
        }
        System.out.println();

        System.out.println("* Read password from wallet with wallet password");
        System.out.println("java -classpath lib/* oracle.security.pki.OracleSecretStoreTextUI -wrl "+walletName+" -viewEntry oracle.security.client.password1");
        System.setIn(new ByteArrayInputStream((walletPassword + "\n").getBytes()));
        try {
            OracleSecretStoreTextUI.main(new String[]{"-wrl", walletName, "-viewEntry", "oracle.security.client.password1"});
        } catch (SecurityException e) {
            // ignore
        }
        System.out.println();

        System.out.println("* Recover wallet without original wallet password and display entries with clear text passwords");
        System.out.println("java -classpath lib/*:oracle-wallet-recover-1.0.jar RecoverWallet "+walletName+" "+recoveredWalletName+" "+recoverPassword);
        RecoverWallet.main(new String[] {walletName, recoveredWalletName, recoverPassword});
        System.out.println();
    }
}
