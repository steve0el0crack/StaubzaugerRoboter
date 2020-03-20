import functools
import random
import sys
import os
import time

x = int(sys.argv[1])
y = int(sys.argv[2])
place_num = [x, y]

world = []
for reihe in range(0, place_num[0]):
        world.append([])
        for place in range(0, place_num[1]):
                world[reihe].append({":x": place, ":y": reihe, ":state" : "", ":distance" : "", ":display" :  ""})	#WORLD
			
def setorigin():
	ocoords = {":x" : random.choice(range(x)), ":y" : random.choice(range(y))}
	world[ocoords[":y"]][ocoords[":x"]][":state"] = u"\u001b[38;5;124mX" + u"\u001b[0m   "   
	world[ocoords[":y"]][ocoords[":x"]][":distance"] = 0
	return ocoords
ocoords = setorigin()

def setinitialdisplay():
	for yi, reihe in enumerate(world):								#DISPLAY ----> RENDERING
                for xi, place in enumerate(reihe):
			if  place[":distance"] != "":
				place[":display"] = place[":distance"]					#NORMAL PLACE ------> DISTANCE   
			if  place[":distance"] == 0:
				place[":display"] = place[":state"]						#ORIGIN -----> X 		


def countplacesby(key, value):										#COUNT by {key : value}
	counter = 0
	for y in range(0, place_num[1]):
                for x in range(0, place_num[0]):
			if  world[y][x][key] == value:
        			counter += 1                     
	return counter 	


def presentworld():
	for y in range(0, place_num[1]):
        	for x in range(0, place_num[0]):
			todisplay = str(world[y][x][":display"])
			sys.stdout.write(todisplay.ljust(4))
		print ""

def searchplacebyrelation(xkey, ykey, xval, yval):
	for reihe in world:
		for place in reihe:
			if place[xkey] == xval and place[ykey] == yval:
				  return place 
def setrelationincoords(coords, key, value): 
	world[coords[1]][coords[0]][key] = value
	
def getcoords(place):											#Search by STRUCT, return COORD
	for y in range(0, place_num[0]):
		for x in range(0, place_num[1]):
			if place == world[y][x]:
				return x, y	
def getplace(coords):
        return [place for yi, element in enumerate(world) for xi, place in enumerate(element) if (xi, yi) == coords]

def searchplaceby(key, value):												#Return STRUCT
	return [place for reihe in world for place in reihe if place[key] == value]

def nebendecoords(origincoord):
        nebendecoords = []
       	for i in [1, -1]:
                ynext = searchplacebyrelation(":x", ":y", origincoord[0], origincoord[1] + i)
                xnext = searchplacebyrelation (":x", ":y", origincoord [0] + i, origincoord [1])
                ycoord = getcoords(ynext)
                xcoord = getcoords(xnext)
                        
                if ycoord != None and ynext[":distance"] ==  "":
                        nebendecoords.append(ycoord)	
                if xcoord != None and xnext[":distance"] ==  "":
                        nebendecoords.append(xcoord)		

        return nebendecoords

TEST = []


def numbering(origins, maxdistance):
        TEST.append (origins)
        try:
                for origin in origins:
                        recurcoords = nebendecoords(origin)
                        map(functools.partial(setrelationincoords, key=":distance", value=maxdistance), recurcoords)
                setinitialdisplay()											#SET WHAT TO RENDER base on DISTANCES
	        presentworld()												#RENDER
	        time.sleep(1)
	        os.system("clear")
	        if countplacesby(":distance", "") > 0:
		        return numbering(recurcoords, maxdistance + 1) 
	        else:
		        return maxdistance, recurcoords
        except UnboundLocalError:
                print TEST
        
def poprelationincoords(coords, key): 
	return world[coords[1]][coords[0]].pop(key)
        
def move(ocoords, cond):                                             #RECURSIVE -----> ONLY one COORD NEEDED, ONLY 1 ELEMENT TO MOVE (X)    

        #SETING DESTINY
        maxdistance, goalcoords = numbering([getcoords(searchplaceby(":distance", 0)[0])], 1)
	presentworld()

        #SETING START
        start = getcoords(searchplaceby(":distance", 0)[0])
        
        
	#RANDOM MOVEMENT ------> NIKITA
        def randmovement(currentcoords):
                inmediategoal = random.choice(map(getcoords, searchplaceby(":distance", getplace(currentcoords)[0][":distance"] + 1)))
                setrelationincoords(inmediategoal, ":display", poprelationincoords(currentcoords, ":display"))
                setrelationincoords(currentcoords, ":display", u"\u001b[38;5;2mX" + u"\u001b[0m   ")
                os.system("clear")
                presentworld()
                time.sleep(1)
                if getplace(currentcoords)[0] [":distance"] != maxdistance:
                        return randmovement(inmediategoal)
                else:
                        return "ONE CYCLE"
        randmovement(start)
move(0, 0)

"""	
	if coords[":y"] + change[1] == ydim - 1:                        #Y EDGE
		nextcoords = {":x" : coords[":x"], ":y" : 0}

	elif coords[":x"] + change[0] == xdim + 1:                      #X EDGE
		nextcoords = {":x" : 0, ":y" : coords[":y"]}
	else:
		nextcoords = {":x" : coords[":x"] + change[0] , ":y" : coords[":y"] + change[1]}

	if detectlampe(getstruct(coords, world)) == "red" or "auto" in getstruct(nextcoords, world).keys():             #TRAFFIC LIGHT
	    continue
	else:
		auto = getstruct(coords, world).pop("auto")
		getstruct(nextcoords, world)["auto"] = auto
		recurindex.append(nextcoords)
		time.sleep(1)
        if cond == 0:
                os.system("clear")
                paintworld()
                print "MAKED"
                return ["a"]
        else:
                sys.stdout.write("RECURSIVE!")
                os.system("clear")
                move(recurindex, cond - 1)
"""





