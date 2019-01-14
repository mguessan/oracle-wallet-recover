import oracle.security.pki.OracleSSOKeyStoreSpi;
import oracle.security.pki.OracleSecretStore;
import oracle.security.pki.OracleWallet;

import java.io.FileInputStream;

/**
 * Recover wallet by creating a new one with a known password.
 */
public class RecoverWallet {
    public static void main(String[] argv) {
        if (argv.length < 2) {
            System.out.println("Usage: java -classpath recoverwallet.jar path/to/sourcewallet path/to/targetwallet [newPassword]");
        } else {
            try {
                OracleWallet targetOracleWallet = new OracleWallet();
                String sourcePath = argv[0];
                String targetPath = argv[1];
                char[] newPassword;
                if (argv.length == 3) {
                    newPassword = argv[2].toCharArray();
                } else {
                    newPassword = System.console().readPassword("Enter new wallet password:");
                }

                targetOracleWallet.create(newPassword);
                if (targetOracleWallet.exists(targetPath)) {
                    System.out.println("Wallet " + targetPath + " already exists");
                } else {
                    OracleSSOKeyStoreSpi secretKeyStore = new OracleSSOKeyStoreSpi();
                    secretKeyStore.engineLoad(new FileInputStream(sourcePath + "/cwallet.sso"), null);
                    targetOracleWallet.saveAs(targetPath);
                    targetOracleWallet.saveSSO();

                    for (int i = 1; secretKeyStore.secretStoreContainsAlias("oracle.security.client.connect_string" + i); ++i) {
                        System.out.println(new String(secretKeyStore.secretStoreGetSecret("oracle.security.client.connect_string" + i)) + " " + new String(secretKeyStore.secretStoreGetSecret("oracle.security.client.username" + i)) + " " + new String(secretKeyStore.secretStoreGetSecret("oracle.security.client.password" + i)));
                        OracleSecretStore secretStore = targetOracleWallet.getSecretStore();
                        secretStore.createCredential(secretKeyStore.secretStoreGetSecret("oracle.security.client.connect_string" + i), secretKeyStore.secretStoreGetSecret("oracle.security.client.username" + i), secretKeyStore.secretStoreGetSecret("oracle.security.client.password" + i));
                        targetOracleWallet.setSecretStore(secretStore);
                        targetOracleWallet.save();
                    }
                }
            } catch (Exception e) {
                System.out.println("Exception: " + e + " " + e.getMessage());
            }
        }

    }
}
