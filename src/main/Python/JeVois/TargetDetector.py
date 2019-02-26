#import necessary components
import cv2
import numpy as np
import math 

squares = []
badPolys = []
sd = ShapeDetector()
centers = []
Moments = []
points = []
counter = 0


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
    print("bottomleft: "+ str(BottomLeft))
    h = calcDistance(TopLeft, BottomLeft)
    Vertical = [TopLeft[0], BottomLeft[1]]
    x = BottomLeft[0] - Vertical[0]
    #print("h: " + str(h))
    #print("x: " + str(x))
    rTheta = math.asin(float(x/h))
    theta = math.degrees(rTheta)
    return theta

def calculateTargetOffsetX(inputImage, Moment1, Moment2, Threshold):
    height, width = inputImage.shape
    imCenterX = width / 2
    cX1 = int((Moment1["m10"] / Moment1["m00"]) * 1.0)
    cX2 = int((Moment2["m10"] / Moment2["m00"]) * 1.0)
    cY1 = int((Moment1["m01"] / Moment1["m00"]) * 1.0)
    cY2 = int((Moment2["m01"] / Moment2["m00"]) * 1.0)

    CenterOfTarget = (cX1 + cX2) / 2
    TargetPixelOffsetX = CenterOfTarget - imCenterX;
    if(TargetPixelOffsetX < -Threshold):
        direction = "Left";
    elif(TargetPixelOffsetX > Threshold):
        direction = "Right";
    else:
        direction = "Center";
    return direction, TargetPixelOffsetX

def execute(binImage):
    im2, contours, hierarchy  = cv2.findContours(binImage, cv2.RETR_CCOMP, cv2.CHAIN_APPROX_TC89_KCOS)

    for c in contours:
    if (contours != None) and (len(contours) > 0):
        cnt_area = cv2.contourArea(c)
        hull = cv2.convexHull(c , 1)
        counter += 1
        hull_area = cv2.contourArea(hull)  #Used in Solidity calculation
       
        M = cv2.moments(c)
        rect = cv2.minAreaRect(c)
        #process potential targets
        angle = findRotationAngle(rect) #numbers rectangles right to left
        Moments.append(M)
        cX = int((M["m10"] / M["m00"]) * 1.0)
        cY = int((M["m01"] / M["m00"])* 1.0)
        centers.append((cX, cY))
        shape = sd.detect(c)
        c = c.astype("float")
        c = c.astype("int")
        cv2.drawContours(img, [c], -1, (0, 255, 0), 2)
        cv2.putText(img, str(angle) , (cX, cY), cv2.FONT_HERSHEY_SIMPLEX, 0.5, (255, 255, 255), 2)

offsetDirection,offsetMagnitude = calculateTargetOffsetX(binImage, Moments[0], Moments[1], Pxthreshold)


