#import necessary components
import cv2
import numpy as np
import math

img = cv2.imread("Target1.png")

cv2.imshow("Target", img)
cv2.waitKey(0)
cv2.destroyAllWindows()