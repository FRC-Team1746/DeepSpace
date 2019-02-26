import libjevois as jevois
import cv2
import numpy as np

## Track 2019 object
#
# Add some description of your module here.
#
# @author 
# 
# @videomapping YUYV 640 480 30 YUYV 640 480 30 Team1746 OTTO2019
# @email 
# @address 123 first street, Los Angeles CA 90012, USA
# @copyright Copyright (C) 2018 by 
# @mainurl 
# @supporturl 
# @otherurl 
# @license 
# @distribution Unrestricted
# @restrictions None
# @ingroup modules
upperHue = 110
lowerHue = 60
upperSat = 255
lowerSat = 10
upperVal = 255
lowerVal = 100
errode = 2
dilate = 2
approx = 6
area = 100
solidity = .2

class OTTO2019:
    # ###################################################################################################
    ## Constructor
    def __init__(self):
        # Instantiate a JeVois Timer to measure our processing framerate:
        self.timer = jevois.Timer("processing timer", 100, jevois.LOG_INFO)
        
    # ###################################################################################################
    ## Process function with USB output
    def blur(src, radius):
        ksize = int(2 * round(radius) + 1)
        return cv2.blur(src, (ksize, ksize))
        
    
    ## Process function with USB output
    def process(self, inframe, outframe):
        # Get the next camera image (may block until it is captured) and here convert it to OpenCV BGR by default. If
        # you need a grayscale image instead, just use getCvGRAY() instead of getCvBGR(). Also supported are getCvRGB()
        # and getCvRGBA():
        inimg = inframe.getCvBGR()
        
        # Start measuring image processing time (NOTE: does not account for input conversion time): 
        #Truely useless and can be removed
        #self.timer.start()
        #Convert the image from BGR(RGB) to HSV
        hsvImage = cv2.cvtColor( inimg, cv2.COLOR_BGR2HSV)
        
        ## Threshold HSV Image to find specific color
        binImage = cv2.inRange(hsvImage, (lowerHue, lowerSat, lowerVal), (upperHue, upperSat, upperVal))
        
        # Erode image to remove noise if necessary.
        binImage = cv2.erode(binImage, None, iterations = errode)
        #Dilate image to fill in gaps
        binImage = cv2.dilate(binImage, None, iterations = dilate)
        
        #This image is used to display the thresholded image. Bounding Rectangle is added below.
        #Use this image to tune your targeting parameters.
        binOut = cv2.cvtColor(binImage, cv2.COLOR_GRAY2BGR)
        
        ##Finds contours (like finding edges/sides), 'contours' is what we are after
        contours, hierarchy = cv2.findContours(binImage, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_TC89_KCOS)
        
        ##arrays to will hold the good/bad polygons
        squares = []
        badPolys = []
        sd = ShapeDetector()
        #ratio = inimg.shape[0] / float(resized
        ## Parse through contours to find targets
        for c in contours:
            if (contours != None) and (len(contours) > 0):
                cnt_area = cv2.contourArea(c)
                hull = cv2.convexHull(c , 1)
                hull_area = cv2.contourArea(hull)  #Used in Solidity calculation
                p = cv2.approxPolyDP(hull, approx, 1)
                if (cv2.isContourConvex(p) != False) and (len(p) == 4) and (cv2.contourArea(p) >= area): #p=3 triangle,4 rect,>=5 circle
                    M = cv2.moments(c)
                    cX = int((M["m10"] / M["m00"]) * 1.0)
                    cY = int((M["m01"] / M["m00"])* 1.0)
                    shape = sd.detect(c)
                    c = c.astype("float")
                    c = c.astype("int")
                    cv2.drawContours(inimg, [c], -1, (0, 255, 0), 2)
                    cv2.putText(inimg, shape, (cX, cY), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
                
                    filled = cnt_area/hull_area
                    if filled <= solidity: #Used to determine if target is hollow or not
                        squares.append(p)
                else:
                    badPolys.append(p)
                
                
        
        ##BoundingRectangles are just CvRectangles, so they store data as (x, y, width, height)
        ##Calculate and draw the center of the target based on the BoundingRect
        for s in squares:        
            br = cv2.boundingRect(s)
            #Target "x" and "y" center 
            x = br[0] + (br[2]/2)
            y = br[1] + (br[3]/2)

            
        for s in squares:
            if len(squares) > 0:
                #Build "pixels" array to contain info desired to be sent to RoboRio

                cv2.rectangle(binOut, (br[0],br[1]),((br[0]+br[2]),(br[1]+br[3])),(0,0,255), 2,cv2.LINE_AA)



        
        # Convert our BGR output image to video output format and send to host over USB. If your output image is not
        # BGR, you can use sendCvGRAY(), sendCvRGB(), or sendCvRGBA() as appropriate:
        vis = np.concatenate((binOut, inimg), axis=1)
        #outframe.sendCv(binOut)
        outframe.sendCv(vis)
        """        
          img = inframe.getCvBGR()
          #outframe.sendCv(img)
          #blurred = blur(img,2)
          ksize = int(2*round(2)+1)
          blurred = cv2.blur(img, (ksize, ksize))
          hsv = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)
          h, s, v = cv2.split(hsv)
          lowerH = 0;
          lowerS = 145;
          lowerV = 60;
          lower = np.array([lowerH, lowerS, lowerV])
          upper = np.array([255, 255, 255])
          #lower3 = np.array([30, 188, 16])
          #upper3 = np.array([75, 255, 255])
          filtered = cv2.inRange(hsv, lower, upper)
          #filtered3 = cv2.inRange(hsv, lower3, upper3)
          filtered =  cv2.erode(filtered, None, iterations = 2)
          filtered = cv2.dilate(filtered, None, iterations = 2)
          
          ##Finds contours (like finding edges/sides), 'contours' is what we are after
          contours, hierarchy = cv2.findContours(filtered, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_TC89_KCOS)
          
          ##arrays to will hold the good/bad polygons
          squares = []
          badPolys = []
          ap = 3
          ar = 50
          sl = 0.3
          ## Parse through contours to find targets
          for c in contours:
            if (contours != None) and (len(contours) > 0):
                cnt_area = cv2.contourArea(c)
                hull = cv2.convexHull(c , 1)
                hull_area = cv2.contourArea(hull)  #Used in Solidity calculation
                p = cv2.approxPolyDP(hull, ap, 1)
                if (cv2.isContourConvex(p) != False) and (len(p) == 4) and (cv2.contourArea(p) >= ar):
                    filled = cnt_area/hull_area
                    if filled <= sl:
                        squares.append(p)
                else:
                    badPolys.append(p)
                    
          ##BoundingRectangles are just CvRectangles, so they store data as (x, y, width, height)
          ##Calculate and draw the center of the target based on the BoundingRect
          for s in squares:        
            br = cv2.boundingRect(s)
            #Target "x" and "y" center 
            x = br[0] + (br[2]/2)
            y = br[1] + (br[3]/2)
          
          for s in squares:
              if len(squares) > 0:
                cv2.putText(img, "Tracking", (3, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255),1, cv2.LINE_AA)
                cv2.putText(img, str(x), (3, 40), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255),1, cv2.LINE_AA)
                cv2.putText(img, str(y), (3, 60), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255),1, cv2.LINE_AA)
                cv2.rectangle(img, (br[0],br[1]),((br[0]+br[2]),(br[1]+br[3])),(0,0,255), 2,cv2.LINE_AA)
          if not squares:
              cv2.putText(img, "Not Tracking", (3, 20), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255),1, cv2.LINE_AA)
          outimg = img
          height, width, channels = outimg.shape # if outimg is grayscale, change to: height, width = outimg.shape
          outframe.sendCv(filtered,50)
          #cv2.putText(outimg, str((3, 80), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255,255,255), 1, cv2.LINE_AA)
          #kernel = cv2.getStructuringElement(cv2.MORPH_RECT, (2,2), anchor=(1,1))
          #output = cv2.morphologyEx(filtered, cv2.MORPH_CLOSE, kernel,
                          #iterations=3)
          #image, contours, hierarchy = cv2.findContours(output, cv2.RETR_EXTERNAL,cv2.CHAIN_APPROX_SIMPLE)
          #dst = np.zeros(shape=img.shape, dtype=img.dtype)
          #cv2.drawContours(dst, contours, -1, (0, 255, 255), 1)
          #outframe.sendCv(filtered)
          #outframe.sendCv(image)          
        """

class ShapeDetector:
    def __init__(self):
        pass

    def detect(self, c):
        # initialize the shape name and approximate the contour
        shape = "unidentified"
        peri = cv2.arcLength(c, True)
        approx = cv2.approxPolyDP(c, 0.06 * peri, True)
        
        # if the shape is a triangle, it will have 3 vertices
        if len(approx) == 3:
            shape = "triangle"
            
        # if the shape has 4 vertices, it is either a square or a rectangle
        elif len(approx) == 4:
            # compute the bounding box of the contour and use the
            # bounding box to compute the aspect ratio
            (x, y, w, h) = cv2.boundingRect(approx)
            ar = w / float(h)
            # a square will have an aspect ratio that is approximately
            # equal to one, otherwise, the shape is a rectangle
            shape = "square" if ar >= 0.95 and ar <= 1.05 else "rectangle"
        return shape
            