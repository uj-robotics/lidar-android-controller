 function dist(pixel, x,y) {
     var dx = pixel.getX() - x;
     var dy = pixel.getY() - y;
     return Math.sqrt(dx * dx + dy *dy);
 }
 
 function norm(x,y)
 {
     if(x===0 && y === 0) return 0;
     return Math.sqrt(x*x+y*y);
 }
 
 function dot(x1,y1,x2,y2)
 {
     return x1*x2 + y1*y2;
 }
 
 function angle(x1,y1,x2,y2)
 {
     return Math.acos((dot(x1,y1,x2,y2)/(norm(x1,y1)*norm(x2,y2))));
 }
 
 //start with a blank image
 var output = new SimpleImage(480,800);
 var oX = 240, oY = 400;
 for (var pixel of output.values())
 {
     pixel.setRed(0);
     pixel.setGreen(0);
     pixel.setBlue(0);
 }
 
 for(var pixel of output.values())
 {
     var xS = 0, yS = -400;
     var Angle = angle(pixel.getX()-240,pixel.getY()-400,xS,yS);
     //gorny trojkat
     if(Angle <angle(xS,yS,160,-400))
     {
         pixel.setRed(150);
     }
     //boczne trojkaty
     if(Angle < angle(-240,240,xS,yS) && Angle > angle(-240,-240,xS,yS))
     {
         pixel.setGreen(250);
         pixel.setRed(250);
     }
     //dolny trojkat
     if(Angle >angle(xS, yS,-160,400))
     {
         pixel.setRed(150);
     }
 }
 
 for (var pixel of output.values())
 {
     if(dist(pixel,oX,oY) < 30)
     {pixel.setRed(200);
     pixel.setGreen(200);
     pixel.setBlue(200);
     }
     
 }
 

 print(output);
 //var x0 = 240, y0=0
 //print(Math.acos(0.5));
 //print(norm(160,-320))
 print(angle(0,-400,160,-400));