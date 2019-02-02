# SimpleBLOG

SimpleBLOG is a simple blog implementation in Java. You can choose to use the Java Servlet techology (tested on Tomcat 7), or just run the blog as a plain old Java application, which in the background will use the webserver integrated into the JDK.

## Dependencies
There are only a handsful of dependencies needed by the project.
 * JMustache
 * Apache Commons IO
 * Apache Commons Fileupload
 * Servlet container implementation
 * Perl installation
 
### JMustache
This is a small(ish) implementation of the [Mustache](https://mustache.github.io/) written in Java. It is completely free of dependencies and available on Github in my [jMustache](https://github.com/szabogabriel/jMustache) repository.

### Apapche Commons IO and Fileupload
These libs are necessary only for the standalone version of the application. They are used during the file upload for parsing the multipart data.

### Servlet container implementation
Servlet containers are also supported when running the application. The tested version is [Apache Tomcat 7](https://tomcat.apache.org/download-70.cgi).

### Perl
Perl is needed to be able to render the [Markdown](https://en.wikipedia.org/wiki/Markdown) pages. 

## Running
To run the blog, the environment variable or parameter `SIMPLEBLOG_HOME` should reference the folder, where the 'resources' folder from the project is to be found. It contains all the blog entries and also the templates for the HTML pages, user data etc.

## Configuring
The application consists of several configuration files, all of which have to be available in the working directory referenced by the `SIMPLEBLOG_HOME` environment variable during the startup of the application.

### Core configuration
The core configurations are to be found in the `[SIMPLEBLOG_HOME]\core.properties`. Currently only the perl executable is needed to be set for the core of the application. Example
~~~~
command.perl=perl
~~~~

### Web application configuration
The web application configurations are to be found in the `[SIMPLEBLOG_HOME]\settings.properties`. It sets the art of using the templates of the application.
~~~~
templates.reload=true
~~~~

### User configuration
The user configuration currently supports only one implementation of the user handling. By default the `[SIMPLEBLOG_HOME]\users\users.properties` file is used for user management. Every user has two predefined keys. One for the salt and one for the password. If there is no salt defined, the password is taken as newly added, and will be hashed upon restart of the application. For example
~~~~
#
#Sat Feb 02 17:31:47 CET 2019
user1@gmail.com.salt=C1uh6
user1@gmail.com.password=-341331642

user2@gmail.com.password=ThisIsThePassword
~~~~
In this case, user2 has been newly added, and his password will be hashed upon the restart of the application. Salt is generated automatically when hashing the password.
