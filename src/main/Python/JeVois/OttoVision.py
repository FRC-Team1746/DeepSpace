#import necessary components
import cv2
import numpy as np
import math 
import PreProcessor as PP
    
def __init__(self):
    pass

def execute(inputImage):
    PP.initialize()
    PreProcessed = PP.execute(inputImage)
    return PreProcessed
