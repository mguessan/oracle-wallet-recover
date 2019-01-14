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

`Enter wallet password:
 oracle.security.client.password1 = myv3rys3cre3tp@ss`
 
## Issue
Everything is fine, you no longer have clear text passwords in your script files and application server datasources...
until you lose the main wallet password (or the previous admin didn't pass it to you).

Sure, you can ask your dba to reset oracle user passwords, but there must be another to recover stored password, right ?

## Wallet structure

## Recover wallet passwords
