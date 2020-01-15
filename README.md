## Regular-Expression-Collections
Regular expression for email, username, password, date, time, file, ip address, HTML tag, hex color value in java class

### Check email address:
```java
RegExpCollections regExpObj = new RegExpCollections("email","jack@harper.com");
if(regExpObj.result() == true) {
  System.out.println("Valid email address");
} else {
  System.out.println("Invalid email address");
}
```

### Check username:
```java
RegExpCollections regExpObj = new RegExpCollections("usernmae","jackharper");
if(regExpObj.result() == true) {
  System.out.println("Valid username");
} else {
  System.out.println("Invalid username");
}
```

### Check photo:
```java
RegExpCollections regExpObj = new RegExpCollections("file","my_photo.jpg","jpg|png|gif");
if(regExpObj.result() == true) {
  System.out.println("Valid photo");
} else {
  System.out.println("Invalid photo");
}
```

### Check documents (only pdf and doc will be allowed):
```java
RegExpCollections regExpObj = new RegExpCollections("file","cv.pdf","pdf|doc");
if(regExpObj.result() == true) {
  System.out.println("Valid document");
} else {
  System.out.println("Invalid document");
}
```
