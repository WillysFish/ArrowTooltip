# ArrowTooltip
ArrowTooltip extends from ConstraintLayout.  
So it very simple to use.   
We can use it as same as ConstrainLayout.  

# Download
### Gradle:  
Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}
```
Add the dependency
```gradle
dependencies {
	 implementation 'com.github.WillysFish:ArrowTooltip:1.0.0'
}
```
### Maven:
Add the JitPack repository to your build file
```xml
<repositories>
  <repository>
      <id>jitpack.io</id>
      <url>https://jitpack.io</url>
  </repository>
</repositories>
```
Add the dependency
```xml
<dependency>
  <groupId>com.github.WillysFish</groupId>
  <artifactId>ArrowTooltip</artifactId>
  <version>1.0.0</version>
</dependency>
```
# How to use? 
There are 4 samples in the below image.  
  
<img src="https://github.com/WillysFish/ArrowTooltip/blob/master/device-2020-09-10-154420.png" height="20%" width="20%" >

Let's see the layout of simple1
```xml
<studio.zewei.willy.arrowtooltiplayout.ArrowTooltipLayout
    android:layout_width="150dp"
    android:layout_height="100dp">

    <androidx.appcompat.widget.AppCompatTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="10dp"
        android:text="Sample 1"
        ... />
</studio.zewei.willy.arrowtooltiplayout.ArrowTooltipLayout>
```
We just need to set width and height.  
  
Of cause you also can custom style as you want.  
Just like other samples.
```xml
<studio.zewei.willy.arrowtooltiplayout.ArrowTooltipLayout
    android:layout_width="150dp"
    android:layout_height="100dp"
    android:layout_marginTop="50dp"
    app:arrowHeight="10dp"
    app:arrowOrientation="start"
    app:arrowWidth="20dp"
    app:positionPercentage="0.3"
    app:tipLayoutColor="#E91E63"
    app:tipLayoutRadius="10dp"
    app:tipLayoutStyle="stroke">

    <androidx.appcompat.widget.AppCompatTextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Sample 4"
        ... />
</studio.zewei.willy.arrowtooltiplayout.ArrowTooltipLayout>
```

We offer some arguments to adjust the ArrowTooltips.  
* arrowWidth  
=> Define the width of triangle.
  
* arrowHeight  
=> Define the height of triangle.
  
* arrowOrientation    
=> Define the orientation of triangle.  
  
* positionPercentage  
=> Define the position of triangle.  
  
* tipLayoutColor  
=> Define the color of ArrowTooltip.  
  
* tipLayoutRadius  
=> Define the radius of ArrowTooltip.  
  
* tipLayoutStyle  
=> Define the ArrowTooltip is Fill or Stroke.  
  

# License
```
Copyright 2020 zewei yan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
