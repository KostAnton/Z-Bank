# 🏦 Z-Bank <img src="/assets/logo.png" alt="Z-Bank Logo" style="height: 189px; width:126;"/>

Bank project by Anton Kost.  
Made for the Computer Science course in Ort Bialik School, Kiryat Bialik.  




## 📄 Screens
- ***Login/Signin Screens***
    - In this screen the user can login to their account. Uppon account login a session id is created and saved in a ```SharedPrefrences``` file.
    - In this screen the user can create an account if they don't have one.
    - Account creation is directly related to the ```Mongo DB``` database in use.
- ***Account Info Screen***
    - This screen will display: User Activiy, account balance and a feature which sends you the data for last months activity.
    - User activity will show the transaction history and messeges from the system.
    - Account balance will show the current balance of the account.
    - The data for last month will be sent to the user's mail inbox. The data is formated in ```.xlsx``` format and show data such as: Detailed transuction history, comparison to last month's data and ```GPT-4``` suggestions for next moves based on the data.
- ***Bank Transaction Screen***
    - In this screen you can make a transaction to a specific person which is specified by the bank account number.
- ***Friends***
    - In this screen you will be able to see friends, their Z-Level (which will be based on savings), their profiles and you compared to them.
    - You will be able to add friends by their name.
- ***Settings***
    - In this screen you will be able to configure your, name, password and e-mail.
    - You will be able to stop notifications from comming.

## 🎓 Data Classes
- ```User.class```
    - The ```User``` class will represent a single user.
    - The private fields that the ```User``` class will contain are: ```name ,password ,id, friends[], zLevel``` 
- ```Account.class```
    - The ```Account``` class will represent a single bank account in the system.
    - The private fields that the ```Account``` class will contain are: ``` owner, currentBalance, savingsBalance, transactions[]```

- ```Transaction.class```
    - The ```Transaction``` class will represent a single transaction that is made between two users or a user to his savings account.
    - The private fields that the ```Transaction``` class will contain are: ``` id, time, amount, participants[]  ```  

- ```Admin.class```
    - The ```Adming``` class is a subclass of the ```User``` class and will represent an admin of the system.
    - The private fields that the ```Admin``` class will contain are: ``` (User inherited fields)```.
    - The ```Admin``` will have permissions to view the Database data as it is, such as all the transactions, all the active users and much more.
- ```Database.class (Singelton)```
    - The ``` Database``` class will represent the ``` MongoDb``` Database that we are connected to.
    - There will be no private fields to the ```Database``` class.
    - All the functionality of the class will be done through getters and setters.
    - The ``` Singelton ``` Design Pattern will help us insure that there will be only one instance of the Database created at each run of the app. 
- ```Levels (Enum)```
    - The ```Levels``` enum will contain the key and value pairs for each level in the zLevel system.
    - The levels will go 1 through 10 and will reset each month based on savings progress.
---


## User Class Code
```java
class User{
    private String name;
    private String password;
    private UUID id;
    private User[] friends;
    private Levels level;

    public String getName(){
        return this.name;
    }
    public void setName(String name){
        this.name = name;
    }
    ...
}
```

| | | |
|:-------------------------:|:-------------------------:|:-------------------------:|
|<img width="1000" alt="Login Screen" src="/assets//loginScreen.png">  Login Screen |  <img width="1000" alt="Account Screen" src="/assets//AccountPageScreen.png"> Account Screen|<img width="1000" alt="Transaction Screen" src="/assets//BankTransactionScreen.png"> Bank Transaction |
|  <img width="1000" alt="Settings Screen" src="/assets//SettingsScreen.png"> Settings Screen | <img width="1000" alt="Friends Screen" src="/assets//FriendsScreen.png"> Friends Screen |  <img width="1000" alt="Logo" src="/assets//logo.png"> Z-Bank |
---
![](/assets//PosterImageZ-Bank.png "Poster Image")