import functools
import random
import sys
import os
import time

def presentworld (world):
        map (lambda place: sys.stdout.write (str (place) + "\n"), world)


world_dims = {":x" : int(sys.argv[1]), ":y" : int(sys.argv[2])}
world = [{":x" : x, ":y" : y, ":state" : "", ":distance" : "", ":display" : ""} for y in range(world_dims[":y"]) for x in range(world_dims[":x"])]

def setrelationincoords(coords, key, value):
        for place in world:
                if place[":x"] == coords [":x"] and place [":y"] == coords [":y"]:
                        place [key] = value

def setorigin():
	origin_coords = {":x" : random.choice(range(x)), ":y" : random.choice(range(y))}
        map ( lambda pair:
              setrelationincoords (origin_coords, pair [":key"], pair [":value"] ),
              [{":key" : ":state", ":value" : u"\u001b[38;5;124mX" + u"\u001b[0m   "},
               {":key" : ":distance", ":value" : 0}])
        return origin_coords
origin_coords = setorigin()

def countplacesby(key, value):										#COUNT by {key : value}
        return len (filter (lambda place: value == place[key], world))

def setnumberingdisplay():
        for place in world:
                place [":display"] = place [":distance"]
def setstatedisplay():
        for place in world:
                if place [":state"] != "":
                        place [":display"] = place [":state"]

def presentworld():
        for i, place in enumerate (world):
                todisplay = str (place[":display"])
                sys.stdout.write(todisplay.ljust(4))
                if ((i + 1) %  world_dims [":x"]) == 0 and i != 0:
                        print ""
        print ""
       
def searchplacebytworelations(xkey, ykey, xval, yval):
        return filter (lambda place: xval == place [xkey] and yval == place[ykey], world)

def searchplacebycoords(coords):
        return filter (lambda place: place [":x"] == coords [":x"] and place [":y"] == coords [":y"], world)[0]

def searchplacebyonerelation(key, value):												#Return STRUCT
	return filter (lambda place: val == place[key], world)



def getdistancebetweencoords (coords1, coords2):
        return {":coords" : coords2, ":distance" : abs(coords1 [":x"] - coords2 [":x"]) + abs(coords1[":y"] - coords2[":y"])} 

def getnebendecoords(origincoords):
        return filter (lambda data: data [":distance"] == 1,
                       map (lambda place: getdistancebetweencoords (origincoords, {":x" : place [":x"], ":y" : place [":y"]}),
                            world))

def not_numbered (coords):
         if searchplacebycoords (coords) [":distance"] == "":
                 return True
         else:
                 return False

def numbering(origins, maxdistance):
        recurcoords = []
        for origin in origins:
                map (lambda data: recurcoords.append (data [":coords"]),
                     filter (lambda data: not_numbered(data [":coords"]),
                             getnebendecoords (origin)))
                map(lambda data: setrelationincoords (data [":coords"], ":distance", maxdistance),
                    filter(lambda data: not_numbered(data[":coords"]),
                           getnebendecoords(origin)))
        setnumberingdisplay()						       #SET WHAT TO RENDER base on DISTANCES
        setstatedisplay ()
        presentworld()							      	#RENDER
	time.sleep(0.1)
	os.system("clear")
	if countplacesby(":distance", "") > 0:
		return numbering(recurcoords, maxdistance + 1) 
	else:
                return maxdistance, recurcoords


def poprelationincoords(coords, key): 
	return searchplacebycoords (coords).pop(key)
        
def move(origin_coords, cond):                             #RECURSIVE -----> ONLY one COORD NEEDED, ONLY 1 ELEMENT TO MOVE (X)    

        #SETING DESTINY
        maxdistance, goalcoords = numbering([origin_coords], 1)
	presentworld()

        #SETING START
        start = origin_coords
        
        
	#RANDOM MOVEMENT ------> NIKITA
        def randmovement(currentcoords):

                inmediategoal = random.choice(map (lambda data: data [":coords"], getnebendecoords(currentcoords)))
                print "INMEDIATE RANDOM GOAL"
                print inmediategoal
                
                setrelationincoords(inmediategoal, ":display", poprelationincoords(currentcoords, ":display"))
                setrelationincoords(currentcoords, ":display", u"\u001b[38;5;2mX" + u"\u001b[0m   ")
                os.system("clear")
                presentworld()
                time.sleep(0.1)
                if countplacesby(":display", u"\u001b[38;5;2mX" + u"\u001b[0m   ") < len (world) - 1:
                        return randmovement(inmediategoal)
                else:
                        return "ONE CYCLE"
        randmovement(start)
move(origin_coords, 0)


