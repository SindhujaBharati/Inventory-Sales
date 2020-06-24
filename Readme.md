#Purchase Management System

Here the enterprise is Inventory Management System which has below three sub systems:
1)Purchase Managment System
2)Stock Managment System
3)Sales Management System

#Adding a purchase details take care of two things
1)If stock whose purchase is done already present in Stock DB.The stock details are updated with current count+purchase count by the help of rest Template.
2)If stock is not present whose purchase is done,new stock object is created and saved in Stock DB by Rest Template.

#Adding a sales details take care of two things
1)Stock DB is checked via Rest Template if stock available sales added and stock count updated with current count-Sales count.
2)If stock is not present whose sales is requested-Out of stock message displayed.




