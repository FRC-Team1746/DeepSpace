#import necessary components
import cv2
import numpy as np
import math 
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

def calculateTargetOffsetX(inputImage, Moment1, Moment2, Threshold):
    height, width = inputImage.shape;
    imCenterX = width / 2;
    cX1 = int((Moment1["m10"] / Moment1["m00"]) * 1.0);
    cX2 = int((Moment2["m10"] / Moment2["m00"]) * 1.0);
    cY1 = int((Moment1["m01"] / Moment1["m00"]) * 1.0);
    cY2 = int((Moment2["m01"] / Moment2["m00"]) * 1.0);

    CenterOfTarget = (cX1 + cX2) / 2;
    TargetPixelOffsetX = CenterOfTarget - imCenterX;
    if(TargetPixelOffsetX < -Threshold):
        direction = "Left";
    elif(TargetPixelOffsetX > Threshold):
        direction = "Right";
    else:
        direction = "Center";
    return direction, TargetPixelOffsetX


############################
## initial Parameters ######
upperHue = 110
lowerHue = 60
upperSat = 255
lowerSat = 10
upperVal = 255
lowerVal = 100
erode = 2
dilate = 2
approx = 6
area = 100
solidity = .2
Pxthreshold = 10
#############################
#############################

img = cv2.imread("Target1.png")


#Convert the image from BGR(RGB) to HSV
hsvImage = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

## Threshold HSV Image to find specific color
binImage = cv2.inRange(hsvImage, (lowerHue, lowerSat, lowerVal), (upperHue, upperSat, upperVal))

# Erode image to remove noise if necessary.
binImage = cv2.erode(binImage, None, iterations = erode)

#Dilate image to fill in gaps
binImage = cv2.dilate(binImage, None, iterations = dilate)

#Find Contours
im2, contours, hierarchy  = cv2.findContours(binImage, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_TC89_KCOS)


##arrays to will hold the good/bad polygons
squares = []
badPolys = []
sd = ShapeDetector()
centers = []
Moments = []
points = []
counter = 0
for c in contours:
    if (contours != None) and (len(contours) > 0):
        cnt_area = cv2.contourArea(c)
        hull = cv2.convexHull(c , 1)
        counter += 1
        hull_area = cv2.contourArea(hull)  #Used in Solidity calculation
        #p = cv2.approxPolyDP(hull, approx, 1)
        #if (cv2.isContourConvex(p) != False) and (len(p) == 4) and (cv2.contourArea(p) >= area): #p=3 triangle,4 rect,>=5 circle
        M = cv2.moments(c)
        rect = cv2.minAreaRect(c)
        box = cv2.boxPoints(rect)
        
        #print("Box: ")
        #print(box)
        #print("\n")
        #for p in points:
        #    print("(" + str(p.x+ "," + str(p.y) + ")"))
        #angle between horizontal and top left
        angle = rect[2]
        Moments.append(M)
        cX = int((M["m10"] / M["m00"]) * 1.0)
        cY = int((M["m01"] / M["m00"])* 1.0)
        centers.append((cX, cY))
        shape = sd.detect(c)
        c = c.astype("float")
        c = c.astype("int")
        cv2.drawContours(img, [c], -1, (0, 255, 0), 2)
        cv2.putText(img, str(angle) , (cX, cY), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
           # filled = cnt_area/hull_area
            #if filled <= solidity: #Used to determine if target is hollow or not
             #   squares.append(p)
        #else:
         #   badPolys.append(p)

offsetDirection,offsetMagnitude = calculateTargetOffsetX(binImage, Moments[0], Moments[1], Pxthreshold)
height, width, channels = img.shape
imCenterX = width / 2
cv2.putText(img, "Center of Image: " + str(imCenterX), (10, 500), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
cv2.putText(img, "Center of Rect1: " + str(centers[0][0]), (10, 525), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
cv2.putText(img, "Center of Rect2: " + str(centers[1][0]), (10, 550), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
TargetOffset1 = imCenterX - centers[0][0]
TargetOffset2 = imCenterX - centers[1][0]
#Currently just checking for absolute centricity
#
#if(direction == "positive"):
#    cv2.putText(img, "Aligned To Right of Target", (10, 600), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
#elif(abs(TargetOffset1) > abs(TargetOffset2)):
#    cv2.putText(img, "Aligned To Left of Target", (10, 600), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
#else:
#    cv2.putText(img, "Aligned in center of target", (10, 600), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)

if(offsetDirection == "Left"):
    cv2.putText(img, "Aligned To Left of Target", (10, 600), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
elif(offsetDirection == "Center"):
    cv2.putText(img, "Aligned in center of target", (10, 600), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
else:
    cv2.putText(img, "Aligned To Right of Target", (10, 600), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)
cv2.imshow('frame', img)

cv2.waitKey(0)
cv2.destroyAllWindows()

def blur(src, radius):
    ksize = int(2 * round(radius) + 1)
    return cv2.blur(src, (ksize, ksize))

def preProcess(inp):
    out = cv2.cvtColor(inp, cv2.COLOR_BGR2HSV)
    out = cv2.inRange(out, (lowerHue, lowerSat, lowerVal), (upperHue, upperSat, upperVal))
    out = cv2.erode(out, None, iterations = erode)
    out = cv2.dilate(out, None, iterations = dilate)
    return out

def findContours(inputImage):
    im2, cont, hier = cv2.findContours(inputImage, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_TC89_KCOS)
    return im2, cont, hier;

def calcDistance(point1, point2):
    x2 = point2[0]
    x1 = point1[0]
    y2 = point2[1]
    y1 = point1[1]

    distance = math.sqrt((x2-x1)**(2) - (y2-y1**(2)))
    return distance

def findRotationAngle(rect):
    box = cv2.boxPoints(rect)
    
    BottomLeft = box[0]
    TopLeft = box[1]
    TopRight = box[2]
    BottomRight = box[3]

    h = calcDistance(TopLeft, BottomLeft)
    Vertical = [TopLeft[0], BottomLeft[1]]
    x = BottomLeft[0] - Vertical[0]
    rTheta = math.asin(float(h/x))
    theta = math.degrees(rTheta)
    return theta


