#import necessary components
import cv2
import numpy as np
import math 

upperHue = 110
lowerHue = 60
upperSat = 255
lowerSat = 10
upperVal = 255
lowerVal = 100
erode = 2
dilate = 2

def __init__(self):
    pass

def blur(src, radius):
    ksize = int(2 * round(radius) + 1)
    return cv2.blur(src, (ksize, ksize))


def initialize():
    upperHue = 110
    lowerHue = 60
    upperSat = 255
    lowerSat = 10
    upperVal = 255
    lowerVal = 100
    erode = 2
    dilate = 2

def execute(inputImage):
    img = blur(inputImage, 2)

    #Convert the image from BGR(RGB) to HSV
    hsvImage = cv2.cvtColor(img, cv2.COLOR_BGR2HSV)

    ## Threshold HSV Image to find specific color
    binImage = cv2.inRange(hsvImage, (lowerHue, lowerSat, lowerVal), (upperHue, upperSat, upperVal))

    # Erode image to remove noise if necessary.
    binImage = cv2.erode(binImage, None, iterations = erode)

    #Dilate image to fill in gaps
    binImage = cv2.dilate(binImage, None, iterations = dilate)

    return binImage