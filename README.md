RDF in-direct paths
============================

Cordova Front-Face camera plugin for Android, supports camera preview and taking photos.

Note: Plug-in has been customised using base code from https://github.com/donaldp24/CanvasCameraPlugin.git 

### Plugin's Purpose
The purpose of the plugin is to capture video to preview front face camera and to take photos with user defined quality / dimension.


## Supported Platforms

- **Android**<br>

currently it only supports Android with minimal settings for front-face camera.


## Dependencies
[Cordova][cordova] will check all dependencies and install them if they are missing.


## Installation
The plugin need to be installed using CLI.

### Adding the Plugin to your project
Through the [Command-line Interface][CLI]:
```bash
# ~~ from master ~~
cordova plugin add https://github.com/aroojfatima/front_face_camera_plugin.git
```

### Removing the Plugin from your project
Through the [Command-line Interface][CLI]:
```bash
cordova plugin rm com.cameraPlugin
```

### PhoneGap Build
Add the following xml to your config.xml to always use the latest version of this plugin:
```xml
<gap:plugin name="com.cameraPlugin" />
```
or to use an specific version:
```xml
<gap:plugin name="com.cameraPlugin" version="1.0" />
```
More informations can be found [here][PGB_plugin].

## ChangeLog


## Using the plugin
The plugin creates the object ```cameraPluginVar``` with the following methods:
```javascript

				var cameraPluginVar=cordova.require("cordova/plugin/cameraPluginVar");
				
				cameraPluginVar.DestinationType = DestinationType;
				cameraPluginVar.PictureSourceType = PictureSourceType;
				cameraPluginVar.EncodingType = EncodingType;
				cameraPluginVar.CameraPosition = CameraPosition;
				cameraPluginVar.FlashMode = FlashMode;

				canvasMain = document.getElementById("camera");
				cameraPluginVar.initialize(canvasMain);
```
### Plugin initialization
The plugin and its methods are not available before the *deviceready* event has been fired.
Have to call [initialize][initialize] with canvas object(canvas tag to preview camera).

```javascript
document.addEventListener('deviceready', function () {
    
    var DestinationType = {
					DATA_URL : 0,
					FILE_URI : 1
				};
				
				var PictureSourceType = {
					PHOTOLIBRARY : 0,
					CAMERA : 1,
					SAVEDPHOTOALBUM : 2
				};
				
				var EncodingType = {
					JPEG : 0,
					PNG : 1
				};
				
				var CameraPosition = {
					BACK : 0,
					FRONT : 1
				};
				
				var CameraPosition = {
					BACK : 1,
					FRONT : 2
				};
				
				var FlashMode = {
					OFF : 0,
					ON : 1,
					AUTO : 2
				};
				
				var cameraPluginVar=cordova.require("cordova/plugin/cameraPluginVar");
				cameraPluginVar.DestinationType = DestinationType;
				cameraPluginVar.PictureSourceType = PictureSourceType;
				cameraPluginVar.EncodingType = EncodingType;
				cameraPluginVar.CameraPosition = CameraPosition;
				cameraPluginVar.FlashMode = FlashMode;

				canvasMain = document.getElementById("camera");
				cameraPluginVar.initialize(canvasMain);
				}, false);
```



## Full Example
```html
<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>Visitor Application - Your Information</title>
		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />
        <title>Hello World</title>
        <script type="text/javascript" charset="utf-8" src="cordova.js"></script>
        <script type="text/javascript" src="camera.js"></script>
		<script type="text/javascript" charset="utf-8">
			document.addEventListener("deviceready",onDeviceReady,false);
		
			// device APIs are available
			//
			function onDeviceReady() {
				var DestinationType = {
					DATA_URL : 0,
					FILE_URI : 1
				};
				
				var PictureSourceType = {
					PHOTOLIBRARY : 0,
					CAMERA : 1,
					SAVEDPHOTOALBUM : 2
				};
				
				var EncodingType = {
					JPEG : 0,
					PNG : 1
				};
				
				var CameraPosition = {
					BACK : 0,
					FRONT : 1
				};
				
				var CameraPosition = {
					BACK : 1,
					FRONT : 2
				};
				
				var FlashMode = {
					OFF : 0,
					ON : 1,
					AUTO : 2
				};
				
				var cameraPluginVar=cordova.require("cordova/plugin/cameraPluginVar");
				cameraPluginVar.DestinationType = DestinationType;
				cameraPluginVar.PictureSourceType = PictureSourceType;
				cameraPluginVar.EncodingType = EncodingType;
				cameraPluginVar.CameraPosition = CameraPosition;
				cameraPluginVar.FlashMode = FlashMode;

				canvasMain = document.getElementById("camera");
				cameraPluginVar.initialize(canvasMain);
				var success = function(message) {
					alert(message);
				}
		
				var failure = function() {
					alert("Error calling  Plugin");
				}
				//cameraPluginVar.show("World", success, failure);
				 var opt = {
                                              quality: 75,
                                              destinationType: cameraPluginVar.DestinationType.DATA_URL,
                                              encodingType: cameraPluginVar.EncodingType.JPEG,
                                              saveToPhotoAlbum:false,
                                              correctOrientation:false,
                                              width:640,
                                              height:480
                                          };
                  cameraPluginVar.start(opt);
						
			}
			function onSuccess(data) {
				//alert("Data -----"+ data.orientation); //JSON.parse(data));
				var smallImage = document.getElementById('smallImage');
				smallImage.src = "data:image/jpeg;base64," +data.imageURI; // URI
			}
			function onFail(data) {
				alert("Ooooooops "+data);
			}
			function onTakePicture() {
				alert("here in pic");
                CanvasCamera.takePicture(onTakeSuccess);
            }

			function onTakeSuccess(data) {
                alert(data);
            }
		 </script>
    </head>
    <body>
         <h2> Camera Position </h2>
           <img src="" id="smallImage" />

    <h1>Test it</h1>
        <canvas id="camera" width="352" height="288" style="border:2px"></canvas>
    </body>
</html>
```