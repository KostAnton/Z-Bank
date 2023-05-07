# 🏦 Z-Bank <img src="/assets/logo.png" alt="Z-Bank Logo" style="height: 189px; width:126;"/>

Bank project by Anton Kost.  
Made for the Computer Science course in Ort Bialik School, Kiryat Bialik.  



## Screens
- ***Login/Signin Screens***
    - In this screen the user can login to their account. Uppon account login a session id is created and saved in a ```SharedPrefrences``` file.
    - In this screen the user can create an account if they don't have one.
    - Account creation is directly related to the ```Mongo DB``` database in use.
- ***Account Info Screen***
    - This screen will display: User Activiy, account balance and a feature which sends you the data for last months activity.
    - User activity will show the transaction history and messeges from the system.
    - Account balance will show the current balance of the account.
    - The data for last month will be sent to the user's mail inbox. The data is formated in ```.xlsx``` format and show data such as: Detailed transuction history, comparison to last month's data and ```GPT-4``` suggestions for next moves based on the data.
- ***Bank Transuction Screen***
    - In this screen you can make a transaction to a specific person which is specified by the bank account number.
- ***
--- 
![](/assets//PosterImageZ-Bank.png "Poster Image")