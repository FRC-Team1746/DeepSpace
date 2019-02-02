#import necessary components
import cv2
import numpy as np
import math 
import OttoVision as OV

img = cv2.imread("Target1.png")

img = OV.execute(img)

cv2.imshow("preprocessed", img)
cv2.waitKey(0)
cv2.destroyAllWindows()