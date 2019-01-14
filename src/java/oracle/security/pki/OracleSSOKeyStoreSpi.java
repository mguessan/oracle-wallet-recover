//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package oracle.security.pki;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.security.AccessController;
import java.security.Key;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.Certificate;
import java.security.cert.CertificateEncodingException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import javax.security.auth.x500.X500Principal;
import oracle.security.crypto.cert.CertificateRequest;
import oracle.security.crypto.core.PrivateKey;
import oracle.security.crypto.core.SignatureException;

public class OracleSSOKeyStoreSpi extends OracleKeyStoreSpi {
    private C041 b = new C041();

    public OracleSSOKeyStoreSpi() {
    }

    public void engineLoad(InputStream var1, char[] var2) throws IOException, NoSuchAlgorithmException, CertificateException {
        OraclePKIDebug.a("OracleSSOKeyStoreImpl: engineLoad");
        AccessController.doPrivileged(new OracleSSOKeyStoreSpi$1(this));
        FileChannel var3 = null;
        FileLock var4 = null;

        try {
            if (var1 instanceof FileInputStream) {
                var3 = ((FileInputStream)var1).getChannel();
                OraclePKIDebug.a("OracleSSOKeyStoreSpi.engineLoad:locking file (shared)..");
                var4 = FileLockProvider.a((File)null, var3, 0L, (long)var1.available(), true);
                OraclePKIDebug.a("OracleSSOKeyStoreSpi.engineLoad:locked file.");
            }

            Object var5 = null;

            char[] var12;
            try {
                int var6 = this.b.b(var1);
                var12 = this.b.a(var1);
                if (var6 == 3) {
                    var12 = this.b.a(var12);
                    OraclePKIDebug.a("OracleSSOKeyStoreImpl: an LSSO wallet");
                }
            } catch (IOException var10) {
                throw new IOException(var10.getMessage());
            }

            super.engineLoad(var1, var12);
        } finally {
            if (var4 != null) {
                OraclePKIDebug.a("OracleSSOKeyStoreSpi.engineLoad:releasing lock..");
                var4.release();
            }

            if (var3 != null) {
                OraclePKIDebug.a("OracleSSOKeyStoreSpi.engineLoad:closing channel..");
                var3.close();
            }

        }

    }

    byte[] a(InputStream var1, char[] var2) throws IOException, OracleSecretStoreException {
        Object var3 = null;
        boolean var4 = false;

        char[] var10;
        int var11;
        try {
            var11 = this.b.b(var1);
            var10 = this.b.a(var1);
        } catch (IOException var9) {
            throw new IOException(var9.getMessage());
        }

        byte[] var5 = super.a(var1, var10);
        Object var6 = null;

        byte[] var12;
        try {
            var12 = this.b.a(var10, var11);
        } catch (IOException var8) {
            throw new IOException(var8.getMessage());
        }

        ByteArrayOutputStream var7 = new ByteArrayOutputStream(var12.length + var5.length);
        var7.write(var12);
        var7.write(var5);
        return var7.toByteArray();
    }

    static void a(InputStream var0, char[] var1, OutputStream var2, char[] var3, int var4) throws IOException {
        a(var0, var1, var2, var3, var4, (byte)0);
    }

    static void a(InputStream var0, char[] var1, OutputStream var2, char[] var3, int var4, byte var5) throws IOException {
        Object var6 = null;
        byte[] var7 = new byte[1024];
        Object var8 = null;
        C041 var9 = new C041();

        char[] var13;
        byte[] var14;
        try {
            var13 = var9.a(var7);
            var14 = var9.a(var13, var4);
            if (var4 == 3) {
                var13 = var9.a(var13);
                OraclePKIDebug.a("OracleSSOKeyStoreImpl: an LSSO wallet");
            }
        } catch (IOException var12) {
            throw new IOException(var12.getMessage());
        }

        boolean var10 = true;

        for(int var11 = 0; var11 < var14.length && var10; ++var11) {
            var10 = var14[var11] == var7[var11];
        }

        var2.write(var14);
        OracleKeyStoreSpi.a(var0, var1, var2, var13, var5);
    }

    byte[] a(InputStream var1, char[] var2, CertificateRequest var3, PrivateKey var4, String var5) throws IOException, KeyStoreException {
        Object var6 = null;

        char[] var9;
        try {
            this.b.b(var1);
            var9 = this.b.a(var1);
        } catch (IOException var8) {
            throw new IOException(var8.getMessage());
        }

        return super.a(var1, var9, var3, var4, var5);
    }

    byte[] a(InputStream var1, char[] var2, OraclePKIX509CertImpl var3, String var4, String var5, boolean var6) throws IOException, KeyStoreException {
        Object var7 = null;

        char[] var10;
        try {
            this.b.b(var1);
            var10 = this.b.a(var1);
        } catch (IOException var9) {
            throw new IOException(var9.getMessage());
        }

        return super.a(var1, var10, var3, var4, var5, var6);
    }

    byte[] a(InputStream var1, char[] var2, OraclePKIX509CertImpl var3, String var4, HashMap var5, boolean var6) throws IOException, KeyStoreException {
        Object var7 = null;

        char[] var10;
        try {
            this.b.b(var1);
            var10 = this.b.a(var1);
        } catch (IOException var9) {
            throw new IOException(var9.getMessage());
        }

        return super.a(var1, var10, var3, var4, var5, var6);
    }

    byte[] a(InputStream var1, char[] var2, X500Principal var3) throws IOException, KeyStoreException, CertificateEncodingException, NoSuchAlgorithmException, UnrecoverableKeyException {
        Object var4 = null;

        char[] var7;
        try {
            this.b.b(var1);
            var7 = this.b.a(var1);
        } catch (IOException var6) {
            throw new IOException(var6.getMessage());
        }

        return super.a(var1, var7, var3);
    }

    byte[] e(InputStream var1, char[] var2, String var3) throws IOException, KeyStoreException, CertificateEncodingException, NoSuchAlgorithmException, UnrecoverableKeyException {
        Object var4 = null;

        char[] var7;
        try {
            this.b.b(var1);
            var7 = this.b.a(var1);
        } catch (IOException var6) {
            throw new IOException(var6.getMessage());
        }

        return super.e(var1, var7, var3);
    }

    byte[] b(InputStream var1, char[] var2, X500Principal var3) throws IOException, KeyStoreException, CertificateEncodingException {
        return this.a(var1, var2, (X500Principal)var3, (String)null, (String)null);
    }

    byte[] a(InputStream var1, char[] var2, X500Principal var3, String var4, String var5) throws IOException, KeyStoreException, CertificateEncodingException {
        Object var6 = null;

        char[] var9;
        try {
            this.b.b(var1);
            var9 = this.b.a(var1);
        } catch (IOException var8) {
            throw new IOException(var8.getMessage());
        }

        return super.a(var1, var9, var3, var4, var5);
    }

    byte[] h(InputStream var1, char[] var2, String var3) throws IOException, KeyStoreException, CertificateEncodingException {
        Object var4 = null;

        char[] var7;
        try {
            this.b.b(var1);
            var7 = this.b.a(var1);
        } catch (IOException var6) {
            throw new IOException(var6.getMessage());
        }

        return super.h(var1, var7, var3);
    }

    byte[] a(InputStream var1, char[] var2, OraclePKIX509CertImpl var3, String var4, String var5) throws IOException, KeyStoreException, CertificateEncodingException {
        Object var6 = null;

        char[] var9;
        try {
            this.b.b(var1);
            var9 = this.b.a(var1);
        } catch (IOException var8) {
            throw new IOException(var8.getMessage());
        }

        return super.a(var1, var9, var3, var4, var5);
    }

    byte[] a(InputStream var1, char[] var2, String var3, HashMap var4) throws IOException, KeyStoreException, CertificateEncodingException {
        Object var5 = null;

        char[] var8;
        try {
            this.b.b(var1);
            var8 = this.b.a(var1);
        } catch (IOException var7) {
            throw new IOException(var7.getMessage());
        }

        return super.a(var1, var8, var3, var4);
    }

    byte[] a(InputStream var1, char[] var2, X500Principal var3, HashMap var4) throws IOException, KeyStoreException, CertificateEncodingException {
        return this.a(var1, var2, (X500Principal)var3, (String)null, (String)null, var4);
    }

    byte[] a(InputStream var1, char[] var2, X500Principal var3, String var4, String var5, HashMap var6) throws IOException, KeyStoreException, CertificateEncodingException {
        Object var7 = null;

        char[] var10;
        try {
            this.b.b(var1);
            var10 = this.b.a(var1);
        } catch (IOException var9) {
            throw new IOException(var9.getMessage());
        }

        return super.a(var1, var10, var3, var4, var5, var6);
    }

    byte[] b(InputStream var1, char[] var2, HashMap var3) throws IOException, KeyStoreException, CertificateEncodingException {
        Object var4 = null;

        char[] var7;
        try {
            this.b.b(var1);
            var7 = this.b.a(var1);
        } catch (IOException var6) {
            throw new IOException(var6.getMessage());
        }

        return super.b(var1, var7, var3);
    }

    byte[] a(InputStream var1, char[] var2, OraclePKIX509CertImpl var3, String var4, String var5, HashMap var6) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
        Object var7 = null;

        char[] var10;
        try {
            this.b.b(var1);
            var10 = this.b.a(var1);
        } catch (IOException var9) {
            throw new IOException(var9.getMessage());
        }

        return super.a(var1, var10, var3, var4, var5, var6);
    }

    byte[] a(InputStream var1, char[] var2, OraclePKIX509CertImpl var3, String var4, String var5, String var6, HashMap var7) throws IOException, KeyStoreException, NoSuchAlgorithmException, CertificateException {
        Object var8 = null;

        char[] var11;
        try {
            this.b.b(var1);
            var11 = this.b.a(var1);
        } catch (IOException var10) {
            throw new IOException(var10.getMessage());
        }

        return super.a(var1, var11, var3, var4, var5, var6, var7);
    }

    public byte[] internalAddPrivateKeyAndCertificateChain(InputStream var1, char[] var2, String var3, Key var4, Certificate[] var5) throws IOException, CertificateEncodingException {
        Object var6 = null;

        char[] var9;
        try {
            int var7 = this.b.b(var1);
            var9 = this.b.a(var1);
            if (var7 == 3) {
                var9 = this.b.a(var9);
            }
        } catch (IOException var8) {
            throw new IOException(var8.getMessage());
        }

        return super.internalAddPrivateKeyAndCertificateChain(var1, var9, var3, var4, var5);
    }

    byte[] a(InputStream var1, char[] var2, HashMap var3) throws IOException, SignatureException, CertificateEncodingException {
        Object var4 = null;

        char[] var7;
        try {
            int var5 = this.b.b(var1);
            var7 = this.b.a(var1);
            if (var5 == 3) {
                var7 = this.b.a(var7);
            }
        } catch (IOException var6) {
            throw new IOException(var6.getMessage());
        }

        return super.a(var1, var7, var3);
    }

    boolean b(InputStream var1, char[] var2) throws IOException, SignatureException, CertificateEncodingException {
        Object var3 = null;

        char[] var6;
        try {
            int var4 = this.b.b(var1);
            var6 = this.b.a(var1);
            if (var4 == 3) {
                var6 = this.b.a(var6);
            }
        } catch (IOException var5) {
            throw new IOException(var5.getMessage());
        }

        return super.b(var1, var6);
    }

    byte[] a(InputStream var1, char[] var2, String var3, String var4, String var5, String var6, HashMap var7) throws IOException, SignatureException, CertificateEncodingException {
        Object var8 = null;

        char[] var11;
        try {
            this.b.b(var1);
            var11 = this.b.a(var1);
        } catch (IOException var10) {
            throw new IOException(var10.getMessage());
        }

        return super.a(var1, var11, var3, var4, var5, var6, var7);
    }

    byte[] a(InputStream var1, char[] var2, HashMap var3, boolean var4) throws Exception {
        Object var5 = null;

        char[] var8;
        try {
            this.b.b(var1);
            var8 = this.b.a(var1);
        } catch (IOException var7) {
            throw new IOException(var7.getMessage());
        }

        return super.a(var1, var8, var3, var4);
    }
}
