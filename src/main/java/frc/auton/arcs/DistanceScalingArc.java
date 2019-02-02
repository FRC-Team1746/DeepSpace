package frc.auton.arcs;

import frc.auton.follower.SrxMotionProfile;
import frc.auton.follower.SrxTrajectory;

public class DistanceScalingArc extends SrxTrajectory {
	
	// WAYPOINTS:
	// (X,Y,degrees)
	// (2.00,13.50,0.00)
	// (5.00,13.50,0.00)
	
 public DistanceScalingArc() {
	super();
	this.highGear = false;
	this.flipped = false;
	centerProfile = new SrxMotionProfile(centerPoints.length, centerPoints);
	}

	
 public DistanceScalingArc(boolean flipped) {
	super();
	this.highGear = false;
	this.flipped = flipped;
	centerProfile = new SrxMotionProfile(centerPoints.length, centerPoints);
	}

	public boolean highGear = false;

	double[][] centerPoints = {
	{0.000,0.000,10.000,0.000},
	{0.044,0.027,10.000,0.000},
	{0.131,0.055,10.000,0.000},
	{0.262,0.082,10.000,0.000},
	{0.437,0.109,10.000,0.000},
	{0.655,0.136,10.000,0.000},
	{0.917,0.164,10.000,0.000},
	{1.222,0.191,10.000,0.000},
	{1.572,0.218,10.000,0.000},
	{1.965,0.246,10.000,0.000},
	{2.401,0.273,10.000,0.000},
	{2.882,0.300,10.000,0.000},
	{3.405,0.327,10.000,0.000},
	{3.973,0.355,10.000,0.000},
	{4.584,0.382,10.000,0.000},
	{5.239,0.409,10.000,0.000},
	{5.938,0.437,10.000,0.000},
	{6.680,0.464,10.000,0.000},
	{7.466,0.491,10.000,0.000},
	{8.295,0.518,10.000,0.000},
	{9.168,0.546,10.000,0.000},
	{10.085,0.573,10.000,0.000},
	{11.046,0.600,10.000,0.000},
	{12.050,0.628,10.000,0.000},
	{13.098,0.655,10.000,0.000},
	{14.189,0.682,10.000,0.000},
	{15.324,0.709,10.000,0.000},
	{16.503,0.737,10.000,0.000},
	{17.726,0.764,10.000,0.000},
	{18.992,0.791,10.000,0.000},
	{20.302,0.819,10.000,0.000},
	{21.655,0.846,10.000,0.000},
	{23.052,0.873,10.000,0.000},
	{24.493,0.900,10.000,0.000},
	{25.977,0.928,10.000,0.000},
	{27.505,0.955,10.000,0.000},
	{29.077,0.982,10.000,0.000},
	{30.693,1.010,10.000,0.000},
	{32.352,1.037,10.000,0.000},
	{34.054,1.064,10.000,0.000},
	{35.801,1.091,10.000,0.000},
	{37.591,1.119,10.000,0.000},
	{39.424,1.146,10.000,0.000},
	{41.302,1.173,10.000,0.000},
	{43.223,1.201,10.000,0.000},
	{45.187,1.228,10.000,0.000},
	{47.196,1.255,10.000,0.000},
	{49.248,1.282,10.000,0.000},
	{51.343,1.310,10.000,0.000},
	{53.483,1.337,10.000,0.000},
	{55.666,1.364,10.000,0.000},
	{57.892,1.392,10.000,0.000},
	{60.163,1.419,10.000,0.000},
	{62.477,1.446,10.000,0.000},
	{64.834,1.474,10.000,0.000},
	{67.235,1.501,10.000,0.000},
	{69.680,1.528,10.000,0.000},
	{72.169,1.555,10.000,0.000},
	{74.701,1.583,10.000,0.000},
	{77.277,1.610,10.000,0.000},
	{79.897,1.637,10.000,0.000},
	{82.560,1.665,10.000,0.000},
	{85.267,1.692,10.000,0.000},
	{88.017,1.719,10.000,0.000},
	{90.812,1.746,10.000,0.000},
	{93.649,1.774,10.000,0.000},
	{96.531,1.801,10.000,0.000},
	{99.456,1.828,10.000,0.000},
	{102.425,1.856,10.000,0.000},
	{105.437,1.883,10.000,0.000},
	{108.494,1.910,10.000,0.000},
	{111.593,1.937,10.000,0.000},
	{114.737,1.965,10.000,0.000},
	{117.924,1.992,10.000,0.000},
	{121.155,2.019,10.000,0.000},
	{124.429,2.047,10.000,0.000},
	{127.747,2.074,10.000,0.000},
	{131.109,2.101,10.000,0.000},
	{134.515,2.128,10.000,0.000},
	{137.964,2.156,10.000,0.000},
	{141.456,2.183,10.000,0.000},
	{144.993,2.210,10.000,0.000},
	{148.573,2.238,10.000,0.000},
	{152.197,2.265,10.000,0.000},
	{155.864,2.292,10.000,0.000},
	{159.575,2.319,10.000,0.000},
	{163.330,2.347,10.000,0.000},
	{167.128,2.374,10.000,0.000},
	{170.970,2.401,10.000,0.000},
	{174.856,2.429,10.000,0.000},
	{178.785,2.456,10.000,0.000},
	{182.758,2.483,10.000,0.000},
	{186.775,2.510,10.000,0.000},
	{190.835,2.538,10.000,0.000},
	{194.939,2.565,10.000,0.000},
	{199.087,2.592,10.000,0.000},
	{203.278,2.620,10.000,0.000},
	{207.513,2.647,10.000,0.000},
	{211.792,2.674,10.000,0.000},
	{216.114,2.701,10.000,0.000},
	{220.480,2.729,10.000,0.000},
	{224.889,2.756,10.000,0.000},
	{229.343,2.783,10.000,0.000},
	{233.840,2.811,10.000,0.000},
	{238.380,2.838,10.000,0.000},
	{242.964,2.865,10.000,0.000},
	{247.592,2.892,10.000,0.000},
	{252.264,2.920,10.000,0.000},
	{256.979,2.947,10.000,0.000},
	{261.738,2.974,10.000,0.000},
	{266.541,3.002,10.000,0.000},
	{271.387,3.029,10.000,0.000},
	{276.277,3.056,10.000,0.000},
	{281.210,3.083,10.000,0.000},
	{286.187,3.111,10.000,0.000},
	{291.208,3.138,10.000,0.000},
	{296.273,3.165,10.000,0.000},
	{301.381,3.193,10.000,0.000},
	{306.533,3.220,10.000,0.000},
	{311.728,3.247,10.000,0.000},
	{316.967,3.274,10.000,0.000},
	{322.250,3.302,10.000,0.000},
	{327.576,3.329,10.000,0.000},
	{332.946,3.356,10.000,0.000},
	{338.360,3.384,10.000,0.000},
	{343.818,3.411,10.000,0.000},
	{349.319,3.438,10.000,0.000},
	{354.863,3.465,10.000,0.000},
	{360.452,3.493,10.000,0.000},
	{366.084,3.520,10.000,0.000},
	{371.760,3.547,10.000,0.000},
	{377.479,3.575,10.000,0.000},
	{383.242,3.602,10.000,0.000},
	{389.049,3.629,10.000,0.000},
	{394.899,3.656,10.000,0.000},
	{400.793,3.684,10.000,0.000},
	{406.731,3.711,10.000,0.000},
	{412.712,3.738,10.000,0.000},
	{418.737,3.766,10.000,0.000},
	{424.806,3.793,10.000,0.000},
	{430.918,3.820,10.000,0.000},
	{437.074,3.847,10.000,0.000},
	{443.274,3.875,10.000,0.000},
	{449.517,3.902,10.000,0.000},
	{455.804,3.929,10.000,0.000},
	{462.135,3.957,10.000,0.000},
	{468.509,3.984,10.000,0.000},
	{474.927,4.011,10.000,0.000},
	{481.388,4.038,10.000,0.000},
	{487.894,4.066,10.000,0.000},
	{494.443,4.093,10.000,0.000},
	{501.035,4.120,10.000,0.000},
	{507.671,4.148,10.000,0.000},
	{514.351,4.175,10.000,0.000},
	{521.075,4.202,10.000,0.000},
	{527.842,4.230,10.000,0.000},
	{534.653,4.257,10.000,0.000},
	{541.507,4.284,10.000,0.000},
	{548.406,4.311,10.000,0.000},
	{555.347,4.339,10.000,0.000},
	{562.333,4.366,10.000,0.000},
	{569.362,4.393,10.000,0.000},
	{576.435,4.421,10.000,0.000},
	{583.551,4.448,10.000,0.000},
	{590.711,4.475,10.000,0.000},
	{597.915,4.502,10.000,0.000},
	{605.163,4.530,10.000,0.000},
	{612.454,4.557,10.000,0.000},
	{619.789,4.584,10.000,0.000},
	{627.167,4.612,10.000,0.000},
	{634.589,4.639,10.000,0.000},
	{642.055,4.666,10.000,0.000},
	{649.564,4.693,10.000,0.000},
	{657.117,4.721,10.000,0.000},
	{664.714,4.748,10.000,0.000},
	{672.355,4.775,10.000,0.000},
	{680.039,4.803,10.000,0.000},
	{687.766,4.830,10.000,0.000},
	{695.538,4.857,10.000,0.000},
	{703.353,4.884,10.000,0.000},
	{711.211,4.912,10.000,0.000},
	{719.114,4.939,10.000,0.000},
	{727.060,4.966,10.000,0.000},
	{735.049,4.994,10.000,0.000},
	{743.083,5.021,10.000,0.000},
	{751.160,5.048,10.000,0.000},
	{759.280,5.075,10.000,0.000},
	{767.445,5.103,10.000,0.000},
	{775.653,5.130,10.000,0.000},
	{783.904,5.157,10.000,0.000},
	{792.200,5.185,10.000,0.000},
	{800.538,5.212,10.000,0.000},
	{808.921,5.239,10.000,0.000},
	{817.347,5.266,10.000,0.000},
	{825.817,5.294,10.000,0.000},
	{834.331,5.321,10.000,0.000},
	{842.888,5.348,10.000,0.000},
	{851.489,5.376,10.000,0.000},
	{860.134,5.403,10.000,0.000},
	{868.822,5.430,10.000,0.000},
	{877.554,5.457,10.000,0.000},
	{886.329,5.485,10.000,0.000},
	{895.148,5.512,10.000,0.000},
	{904.011,5.539,10.000,0.000},
	{912.918,5.567,10.000,0.000},
	{921.868,5.594,10.000,0.000},
	{930.862,5.621,10.000,0.000},
	{939.899,5.648,10.000,0.000},
	{948.980,5.676,10.000,0.000},
	{958.105,5.703,10.000,0.000},
	{967.274,5.730,10.000,0.000},
	{976.486,5.758,10.000,0.000},
	{985.742,5.785,10.000,0.000},
	{995.041,5.812,10.000,0.000},
	{1004.384,5.839,10.000,0.000},
	{1013.771,5.867,10.000,0.000},
	{1023.201,5.894,10.000,0.000},
	{1032.675,5.921,10.000,0.000},
	{1042.193,5.949,10.000,0.000},
	{1051.755,5.976,10.000,0.000},
	{1061.360,6.003,10.000,0.000},
	{1071.008,6.030,10.000,0.000},
	{1080.701,6.058,10.000,0.000},
	{1090.437,6.085,10.000,0.000},
	{1100.216,6.112,10.000,0.000},
	{1110.040,6.140,10.000,0.000},
	{1119.907,6.167,10.000,0.000},
	{1129.818,6.194,10.000,0.000},
	{1139.772,6.221,10.000,0.000},
	{1149.770,6.249,10.000,0.000},
	{1159.812,6.276,10.000,0.000},
	{1169.897,6.303,10.000,0.000},
	{1180.026,6.331,10.000,0.000},
	{1190.198,6.358,10.000,0.000},
	{1200.415,6.385,10.000,0.000},
	{1210.675,6.412,10.000,0.000},
	{1220.978,6.440,10.000,0.000},
	{1231.326,6.467,10.000,0.000},
	{1241.717,6.494,10.000,0.000},
	{1252.151,6.522,10.000,0.000},
	{1262.629,6.549,10.000,0.000},
	{1273.151,6.576,10.000,0.000},
	{1283.717,6.603,10.000,0.000},
	{1294.326,6.631,10.000,0.000},
	{1304.979,6.658,10.000,0.000},
	{1315.673,6.684,10.000,0.000},
	{1326.324,6.657,10.000,0.000},
	{1336.931,6.629,10.000,0.000},
	{1347.494,6.602,10.000,0.000},
	{1358.014,6.575,10.000,0.000},
	{1368.490,6.548,10.000,0.000},
	{1378.922,6.520,10.000,0.000},
	{1389.311,6.493,10.000,0.000},
	{1399.656,6.466,10.000,0.000},
	{1409.958,6.438,10.000,0.000},
	{1420.215,6.411,10.000,0.000},
	{1430.429,6.384,10.000,0.000},
	{1440.600,6.357,10.000,0.000},
	{1450.726,6.329,10.000,0.000},
	{1460.810,6.302,10.000,0.000},
	{1470.849,6.275,10.000,0.000},
	{1480.845,6.247,10.000,0.000},
	{1490.797,6.220,10.000,0.000},
	{1500.705,6.193,10.000,0.000},
	{1510.570,6.165,10.000,0.000},
	{1520.391,6.138,10.000,0.000},
	{1530.169,6.111,10.000,0.000},
	{1539.903,6.084,10.000,0.000},
	{1549.593,6.056,10.000,0.000},
	{1559.239,6.029,10.000,0.000},
	{1568.842,6.002,10.000,0.000},
	{1578.401,5.974,10.000,0.000},
	{1587.917,5.947,10.000,0.000},
	{1597.389,5.920,10.000,0.000},
	{1606.817,5.893,10.000,0.000},
	{1616.201,5.865,10.000,0.000},
	{1625.542,5.838,10.000,0.000},
	{1634.839,5.811,10.000,0.000},
	{1644.093,5.783,10.000,0.000},
	{1653.303,5.756,10.000,0.000},
	{1662.469,5.729,10.000,0.000},
	{1671.592,5.702,10.000,0.000},
	{1680.671,5.674,10.000,0.000},
	{1689.706,5.647,10.000,0.000},
	{1698.698,5.620,10.000,0.000},
	{1707.645,5.592,10.000,0.000},
	{1716.550,5.565,10.000,0.000},
	{1725.410,5.538,10.000,0.000},
	{1734.227,5.511,10.000,0.000},
	{1743.001,5.483,10.000,0.000},
	{1751.730,5.456,10.000,0.000},
	{1760.416,5.429,10.000,0.000},
	{1769.059,5.401,10.000,0.000},
	{1777.657,5.374,10.000,0.000},
	{1786.212,5.347,10.000,0.000},
	{1794.724,5.320,10.000,0.000},
	{1803.191,5.292,10.000,0.000},
	{1811.615,5.265,10.000,0.000},
	{1819.996,5.238,10.000,0.000},
	{1828.332,5.210,10.000,0.000},
	{1836.626,5.183,10.000,0.000},
	{1844.875,5.156,10.000,0.000},
	{1853.081,5.129,10.000,0.000},
	{1861.243,5.101,10.000,0.000},
	{1869.361,5.074,10.000,0.000},
	{1877.436,5.047,10.000,0.000},
	{1885.467,5.019,10.000,0.000},
	{1893.454,4.992,10.000,0.000},
	{1901.398,4.965,10.000,0.000},
	{1909.298,4.938,10.000,0.000},
	{1917.155,4.910,10.000,0.000},
	{1924.968,4.883,10.000,0.000},
	{1932.737,4.856,10.000,0.000},
	{1940.462,4.828,10.000,0.000},
	{1948.144,4.801,10.000,0.000},
	{1955.782,4.774,10.000,0.000},
	{1963.377,4.747,10.000,0.000},
	{1970.928,4.719,10.000,0.000},
	{1978.435,4.692,10.000,0.000},
	{1985.898,4.665,10.000,0.000},
	{1993.318,4.637,10.000,0.000},
	{2000.694,4.610,10.000,0.000},
	{2008.027,4.583,10.000,0.000},
	{2015.316,4.556,10.000,0.000},
	{2022.561,4.528,10.000,0.000},
	{2029.763,4.501,10.000,0.000},
	{2036.921,4.474,10.000,0.000},
	{2044.035,4.446,10.000,0.000},
	{2051.105,4.419,10.000,0.000},
	{2058.132,4.392,10.000,0.000},
	{2065.116,4.365,10.000,0.000},
	{2072.055,4.337,10.000,0.000},
	{2078.951,4.310,10.000,0.000},
	{2085.803,4.283,10.000,0.000},
	{2092.612,4.255,10.000,0.000},
	{2099.377,4.228,10.000,0.000},
	{2106.098,4.201,10.000,0.000},
	{2112.776,4.174,10.000,0.000},
	{2119.410,4.146,10.000,0.000},
	{2126.000,4.119,10.000,0.000},
	{2132.547,4.092,10.000,0.000},
	{2139.050,4.064,10.000,0.000},
	{2145.509,4.037,10.000,0.000},
	{2151.925,4.010,10.000,0.000},
	{2158.297,3.983,10.000,0.000},
	{2164.626,3.955,10.000,0.000},
	{2170.910,3.928,10.000,0.000},
	{2177.151,3.901,10.000,0.000},
	{2183.349,3.873,10.000,0.000},
	{2189.502,3.846,10.000,0.000},
	{2195.613,3.819,10.000,0.000},
	{2201.679,3.792,10.000,0.000},
	{2207.702,3.764,10.000,0.000},
	{2213.681,3.737,10.000,0.000},
	{2219.616,3.710,10.000,0.000},
	{2225.508,3.682,10.000,0.000},
	{2231.356,3.655,10.000,0.000},
	{2237.161,3.628,10.000,0.000},
	{2242.922,3.601,10.000,0.000},
	{2248.639,3.573,10.000,0.000},
	{2254.312,3.546,10.000,0.000},
	{2259.942,3.519,10.000,0.000},
	{2265.528,3.491,10.000,0.000},
	{2271.071,3.464,10.000,0.000},
	{2276.570,3.437,10.000,0.000},
	{2282.025,3.409,10.000,0.000},
	{2287.436,3.382,10.000,0.000},
	{2292.804,3.355,10.000,0.000},
	{2298.128,3.328,10.000,0.000},
	{2303.409,3.300,10.000,0.000},
	{2308.646,3.273,10.000,0.000},
	{2313.839,3.246,10.000,0.000},
	{2318.989,3.218,10.000,0.000},
	{2324.095,3.191,10.000,0.000},
	{2329.157,3.164,10.000,0.000},
	{2334.175,3.137,10.000,0.000},
	{2339.150,3.109,10.000,0.000},
	{2344.082,3.082,10.000,0.000},
	{2348.969,3.055,10.000,0.000},
	{2353.813,3.027,10.000,0.000},
	{2358.614,3.000,10.000,0.000},
	{2363.370,2.973,10.000,0.000},
	{2368.083,2.946,10.000,0.000},
	{2372.752,2.918,10.000,0.000},
	{2377.378,2.891,10.000,0.000},
	{2381.960,2.864,10.000,0.000},
	{2386.499,2.836,10.000,0.000},
	{2390.993,2.809,10.000,0.000},
	{2395.444,2.782,10.000,0.000},
	{2399.852,2.755,10.000,0.000},
	{2404.215,2.727,10.000,0.000},
	{2408.535,2.700,10.000,0.000},
	{2412.812,2.673,10.000,0.000},
	{2417.044,2.645,10.000,0.000},
	{2421.234,2.618,10.000,0.000},
	{2425.379,2.591,10.000,0.000},
	{2429.481,2.564,10.000,0.000},
	{2433.539,2.536,10.000,0.000},
	{2437.553,2.509,10.000,0.000},
	{2441.524,2.482,10.000,0.000},
	{2445.451,2.454,10.000,0.000},
	{2449.335,2.427,10.000,0.000},
	{2453.174,2.400,10.000,0.000},
	{2456.971,2.373,10.000,0.000},
	{2460.723,2.345,10.000,0.000},
	{2464.432,2.318,10.000,0.000},
	{2468.097,2.291,10.000,0.000},
	{2471.719,2.263,10.000,0.000},
	{2475.296,2.236,10.000,0.000},
	{2478.831,2.209,10.000,0.000},
	{2482.321,2.182,10.000,0.000},
	{2485.768,2.154,10.000,0.000},
	{2489.171,2.127,10.000,0.000},
	{2492.531,2.100,10.000,0.000},
	{2495.847,2.072,10.000,0.000},
	{2499.119,2.045,10.000,0.000},
	{2502.347,2.018,10.000,0.000},
	{2505.532,1.991,10.000,0.000},
	{2508.674,1.963,10.000,0.000},
	{2511.771,1.936,10.000,0.000},
	{2514.825,1.909,10.000,0.000},
	{2517.835,1.881,10.000,0.000},
	{2520.802,1.854,10.000,0.000},
	{2523.725,1.827,10.000,0.000},
	{2526.604,1.800,10.000,0.000},
	{2529.440,1.772,10.000,0.000},
	{2532.232,1.745,10.000,0.000},
	{2534.980,1.718,10.000,0.000},
	{2537.685,1.690,10.000,0.000},
	{2540.346,1.663,10.000,0.000},
	{2542.963,1.636,10.000,0.000},
	{2545.537,1.609,10.000,0.000},
	{2548.067,1.581,10.000,0.000},
	{2550.553,1.554,10.000,0.000},
	{2552.996,1.527,10.000,0.000},
	{2555.395,1.499,10.000,0.000},
	{2557.750,1.472,10.000,0.000},
	{2560.062,1.445,10.000,0.000},
	{2562.330,1.418,10.000,0.000},
	{2564.554,1.390,10.000,0.000},
	{2566.735,1.363,10.000,0.000},
	{2568.872,1.336,10.000,0.000},
	{2570.966,1.308,10.000,0.000},
	{2573.015,1.281,10.000,0.000},
	{2575.022,1.254,10.000,0.000},
	{2576.984,1.227,10.000,0.000},
	{2578.903,1.199,10.000,0.000},
	{2580.778,1.172,10.000,0.000},
	{2582.609,1.145,10.000,0.000},
	{2584.397,1.117,10.000,0.000},
	{2586.141,1.090,10.000,0.000},
	{2587.842,1.063,10.000,0.000},
	{2589.499,1.036,10.000,0.000},
	{2591.112,1.008,10.000,0.000},
	{2592.681,0.981,10.000,0.000},
	{2594.207,0.954,10.000,0.000},
	{2595.689,0.926,10.000,0.000},
	{2597.128,0.899,10.000,0.000},
	{2598.523,0.872,10.000,0.000},
	{2599.874,0.845,10.000,0.000},
	{2601.181,0.817,10.000,0.000},
	{2602.445,0.790,10.000,0.000},
	{2603.666,0.763,10.000,0.000},
	{2604.842,0.735,10.000,0.000},
	{2605.975,0.708,10.000,0.000},
	{2607.064,0.681,10.000,0.000},
	{2608.110,0.653,10.000,0.000},
	{2609.112,0.626,10.000,0.000},
	{2610.070,0.599,10.000,0.000},
	{2610.985,0.572,10.000,0.000},
	{2611.856,0.544,10.000,0.000},
	{2612.683,0.517,10.000,0.000},
	{2613.467,0.490,10.000,0.000},
	{2614.207,0.462,10.000,0.000},
	{2614.903,0.435,10.000,0.000},
	{2615.556,0.408,10.000,0.000},
	{2616.165,0.381,10.000,0.000},
	{2616.730,0.353,10.000,0.000},
	{2617.252,0.326,10.000,0.000},
	{2617.730,0.299,10.000,0.000},
	{2618.164,0.271,10.000,0.000},
	{2618.555,0.244,10.000,0.000},
	{2618.902,0.217,10.000,0.000},
	{2619.205,0.190,10.000,0.000},
	{2619.465,0.162,10.000,0.000},
	{2619.681,0.135,10.000,0.000},
	{2619.853,0.108,10.000,0.000},
	{2619.982,0.080,10.000,0.000},
	{2620.067,0.053,10.000,0.000}	};

}