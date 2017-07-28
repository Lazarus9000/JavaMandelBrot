Mandelbrot render in Java

The project is split up in two classes. One which does all the math and generates images of the mandelbrot set.
The other class handles displaying and interaction, and there are two implementations of this, using different frameworks. Currently the most mature of the two is the one using javaFX. 

Left click zooms in and right click zooms out


There is also an ant build script included, which compiles the javaFX solution to a jar executable - Currently it uses absolute folder paths, but you can make it work on your own system with minor modifications.

Project has been developed in eclipse and related project files are included. Still need to test if this is enough to make the project 'portable'.

To run compiled .jar file in build folder you need to hava a java runtime, http://www.oracle.com/technetwork/java/javase/downloads/jre8-downloads-2133155.html

On windows, linux or osx you can execute .jar files using a terminal or command promt to run the following command 'java -jar filename.jar'.

On windows you can also just open the file using explorer.

Todo: 
Migrate to android - https://www.infoq.com/articles/Building-JavaFX-Android-Apps
Smooth shading - https://en.wikipedia.org/wiki/Mandelbrot_set#Computer_drawings
Other gradients - https://stackoverflow.com/questions/30157346/convert-grayscale-to-color-gradient-in-java
