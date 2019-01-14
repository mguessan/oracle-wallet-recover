# oracle-wallet-recover
Recover passwords from an Oracle wallet.

## Description
Oracle wallet is designed to securely store credentials to access an Oracle database and avoid clear text passwords.

Mkstore command is used to create a new wallet and add credentials:

- Create a wallet:
`mkstore -wrl mywallet -create`

- Add database credentials to the wallet:
`mkstore -wrl mywallet -createCredential oracleserver:1521 dbuser myv3rys3cre3tp@ss`

- List stored credentials:
`mkstore -wrl mywallet -list`

- Display stored password:
`mkstore -wrl mywallet -viewEntry oracle.security.client.password1`

Note that for each calls above you must provide the wallet password:

```
Enter wallet password:
 oracle.security.client.password1 = myv3rys3cre3tp@ss
```
 
## Issue
Everything is fine, you no longer have clear text passwords in your script files and application server datasources...
until you lose the main wallet password (or the previous admin didn't pass it to you).

Sure, you can ask your dba to reset oracle user passwords, but there must be another to recover stored password, right ?

## Wallet structure
Oracle wallet is a directory with two files:
* ewallet.p12
* cwallet.sso

ewallet.p12 is the actual key store in PKCS12 format, cwallet.sso looks like a SSO key...

## Recover wallet password
Use the recoverwallet.jar to create a new wallet with a new password and old wallet content:

`java -jar recoverwallet.jar path/to/sourcewallet path/to/targetwallet [newPassword]`

If the wallet was created with orapki auto_login_local option, you may get an LSSO wallet error message.
In this case, just override current hostname and username to match original wallet location:

```
hostname walletserver
java -Duser.name=walletuser -jar recoverwallet.jar path/to/sourcewallet path/to/targetwallet [newPassword]
```


## Workaround
You can create an Oracle wallet without a SSO key by using orapki without auto_login option instead of mkstore:

`orapki wallet create -wallet mywallet`

However, this means you need to provide the wallet password each time you want to use it, 
which pretty much defeats the initial requirement of scripts and servers: run unattended.

## Build
In order to build this project, you need the Oracle driver, get it from:
https://www.oracle.com/database/technologies/appdev/jdbc.html

Copy jars from official driver package to lib directory. 

## Manage wallet without mkstore scripts
* Create wallet:

`java -Doracle.pki.debug=true -classpath lib/oraclepki.jar;lib/osdt_core.jar;lib/osdt_cert.jar oracle.security.pki.OracleSecretStoreTextUI -wrl mywallet -create -createCredential key username password`

* Add a credential to wallet

`java -Doracle.pki.debug=true -classpath lib/oraclepki.jar;lib/osdt_core.jar;lib/osdt_cert.jar oracle.security.pki.OracleSecretStoreTextUI -wrl mywallet -createCredential username password`

* List wallet content:

`java -Doracle.pki.debug=true -classpath lib/oraclepki.jar;lib/osdt_core.jar;lib/osdt_cert.jar oracle.security.pki.OracleSecretStoreTextUI -wrl mywallet -list`

## Reference

* Oracle documentation:

http://oracle-base.com/articles/10g/secure-external-password-store-10gr2.php

    
    
    
    
