Render mandelbrot with Java

The project is split up in a class which generates images of the mandelbrot set. Here all the math is done.
Two seperate classes uses different frameworks to display and interact with the image. Left click zooms in, right click zooms out and middle mousebutton moves the view.
Currently the most mature of the two is the one using javaFX.

There is also an ant build script included, which compiles the javaFX solution to a jar executable.

Project has been developed in eclipse and related project files are included. Still need to test if this is enough to make the project 'portable'.

To run compiled .jar file in build folder you need to hava a java runtime, http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html
On windows, linux or osx you can execute .jar files using a terminal or command promt to run the following command 'java -jar filename.jar'.
On windows you can also just open the file using explorer.
