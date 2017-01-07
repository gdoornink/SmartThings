/**
 *  Sleeping Activity Routines
 *
 *  Copyright 2017  Greg Doornink
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 *
 */
 
 
 
definition(
    name: "Sleeping Activity Routines",
    namespace: "GTDoor",
    author: "Greg Doornink",
    description: "",
    category: "Convenience",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png",
    iconX3Url: "https://s3.amazonaws.com/smartapp-icons/Convenience/Cat-Convenience@2x.png")



// Set Pages

preferences {
	page(name:"pageInputs")					// Used to select inputs (sleep sensors, presence detectors, sleeping-in switches, etc.)
    page(name:"pageBasics")					// Used for basic scenarios (e.g. "Everyone is asleep", or "First person woke up")
    page(name:"pageGregScenarios")			// Used for Greg-specific scenarios (e.g. "Greg fell asleep" or "Greg woke up")
    page(name:"pageTiffanyScenarios")		// Same thing as above but for Tiffany
    page(name:"pageFinalTouches")			// Used to set name of app and modes to run in
}



// Get and sort available routines

def actions = location.helloHome?.getPhrases()*.label
if (actions) {
	actions.sort()
}



//Define Pages

define pageInputs() {
	dynamicPage(name: "pageInputs", title: "Let's get our inputs.", uninstall: true, nextPage: "pageBasics") {
    	section("Greg") {
        	input "isGregPresent", "capability.presenceSensor", title: "Greg's Presence Sensor", multiple: false, required: true
            input "isGregAwake", "capability.sleepSensor", title: "Greg's Awake Sensor", multiple: false, required: true
            input "isGregSleepingIn", "capability.switch", title: "Greg's Sleeping-In Indicator", multiple: false, required: true
        }
        section("Tiffany") {
        	input "isTiffanyPresent", "capability.presenceSensor", title: "Tiffany's Presence Sensor", multiple: false, required: true
            input "isTiffanyAwake", "capability.sleepSensor", title: "Tiffany's Awake Sensor", multiple: false, required: true
            input "isTiffanySleepingIn", "capability.switch", title: "Tiffany's Sleeping-In Indicator", multiple: false, required: true
        }
    }
}

define pageBasics() {
	dynamicPage(name: "pageBasics", title: "Now let's get the basics down.", uninstall: false, nextPage: "pageGregScenarios") {
    	section("Falling Asleep") {
        	input "actionSomeoneAsleepAnytime", "enum", title: "Routine when ANYONE falls asleep", options: actions, required: false
            input "actionSomeoneAsleepFirst", "enum", title: "Routine when FIRST person falls asleep", options: actions, required: false
            input "actionAllAsleep", "enum", title: "Routine when EVERYONE has fallen asleep", options: actions, required: false
        }
        section("Waking Up") {
        	input "actionSomeoneAwakeAnytime", "enum", title: "Routine when ANYONE wakes up", options: actions, required: false
            input "actionSomeoneAwakeFirst", "enum", title: "Routine when FIRST person wakes up", options: actions, required: false
            input "actionAllAwake", "enum", title: "Routine when EVERYONE has woken up", options: actions, required: false
        }
        section("Waking Up After Sleeping In") {
        	input "actionSomeoneAwakeSleepingIn", "enum", title: "Routine when ANYONE wakes up after sleeping in", options: actions, required: false
            input "actionSomeoneAwakeNotSleepingIn", "enum", title: "Routine when ANYONE wakes up after NOT sleeping in", options: actions, required: false
        }
    }
}

define pageGregScenarios() {
	dynamicPage(name: "pageGregScenarios", title: "Let's start with Greg.", uninstall: false, nextPage: "pageTiffanyScenarios") {
    	section ("Falling Asleep") {
        	input "actionGregAsleepAnytime", "enum", title: "Routine when Greg falls asleep ANYTIME", options: actions, required: false
            input "actionGregAsleepFirst", "enum", title: "Routine when Greg falls asleep FIRST", options: actions, required: false
            input "actionGregAsleepLast", "enum", title: "Routine when Greg falls asleep LAST", options: actions, required: false
        }
        section ("Waking Up") {
        	input "actionGregAwakeAnytime", "enum", title: "Routine when Greg wakes up ANYTIME", options: actions, required: false
            input "actionGregAwakeFirst", "enum", title: "Routine when Greg wakes up FIRST", options: actions, required: false
            input "actionGregAwakeLast", "enum", title: "Routine when Greg wakes up LAST", options: actions, required: false
        }
        section ("Sleeping In?") {
        	input "actionGregAwakeSleepingIn", "enum", title: "Routine when Greg wakes up after sleeping in", options: actions, required: false
            input "actionGregAwakeNotSleepingIn", "enum", title: "Routine when Greg wakes up FIRST when she IS sleeping in", options: actions, required: false
        }
    }
}

define pageTiffanyScenarios() {
	dynamicPage(name: "pageTiffanyScenarios", title: "Tiffany's Turn!", uninstall: false, nextPage: "pageFinalTouches") {
    	section ("Falling Asleep") {
        	input "actionTiffanyAsleepAnytime", "enum", title: "Routine when Tiffany falls asleep ANYTIME", options: actions, required: false
            input "actionTiffanyAsleepFirst", "enum", title: "Routine when Tiffany falls asleep FIRST", options: actions, required: false
            input "actionTiffanyAsleepLast", "enum", title: "Routine when Tiffany falls asleep LAST", options: actions, required: false
        }
        section ("Waking Up") {
        	input "actionTiffanyAwakeAnytime", "enum", title: "Routine ANYTIME Tiffany wakes up ANYTIME", options: actions, required: false
            input "actionTiffanyAwakeFirst", "enum", title: "Routine ANYTIME Tiffany wakes up FIRST", options: actions, required: false
            input "actionTiffanyAwakeLast", "enum", title: "Routine ANYTIME Tiffany wakes up LAST", options: actions, required: false
        }
        section ("Sleeping In?") {
        	input "actionTiffanyAwakeSleepingIn", "enum", title: "Routine when Tiffany wakes up after sleeping in", options: actions, required: false
            input "actionTiffanyAwakeNotSleepingIn", "enum", title: "Routine when Tiffany wakes up FIRST when she IS sleeping in", options: actions, required: false
        }
    }
}

define pageFinalTouches() {
	dynamicPage(name: "pageFinalTouches", title: "Just a couple finishing touches.", uninstall: false, install: true) {
    	label title: "Assign a name (optional)", required: false
        mode title: "Run only in these modes (optional)", required: false
    }
}




def installed() {
	log.debug "Installed with settings: ${settings}"

	initialize()
}

def updated() {
	log.debug "Updated with settings: ${settings}"

	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(gregAwake, "switch.on", heyGregWokeUp)
    subscribe(gregAwake, "switch.off", heyGregFellAsleep)
    subscribe(tiffanyAwake, "switch.on", heyTiffanyWokeUp)
    subscribe(tiffanyAwake, "switch.off", heyTiffanyFellAsleep)
}




// REACTING TO GREG

define heyGregWokeUp(evt) {    // Greg woke up!
	if(isGregPresent.currentValue("presenceSensor") == "present") {    // Only react if Greg is present
    
    	// Actions every time Greg wakes up at home
    	location.helloHome?.execute(actionSomeoneAwakeAnytime)
        location.helloHome?.execute(actionGregAwakeAnytime)
        
        if(tiffanyAwake.currentValue("switch") == "off") {    // Tiffany is still asleep
        
        	// Actions when Greg wakes up first
        	location.helloHome?.execute(actionSomeoneAwakeFirst)
            location.helloHome?.execute(actionGregAwakeFirst)
            
        } else {    // Tiffany is already awake
        	
            // Actions when Greg wakes up last
            location.helloHome?.execute(actionAllAwake)
            location.helloHome?.execute(actionGregAwakeLast)
            
        }
        
    	if(isGregSleepingIn.currentValue("switch") == "off") {    // Greg is not sleeping in
        	
            // Actions when Greg wakes up when NOT sleeping in
            location.helloHome?.execute(actionSomeoneAwakeNotSleepingIn)
            location.helloHome?.execute(actionGregAwakeNotSleepingIn)
        	
        } else {    // Reactions if Greg IS sleeping in
        	
            // Actions when Greg wakes up WHILE sleeping in
            location.helloHome?.execute(actionSomeoneAwakeSleepingIn)
            location.helloHome?.execute(actionGregAwakeSleepingIn)
            
        }
    }
}



define heyGregFellAsleep(evt) {    // Greg fell asleep!
	if(isGregPresent.currentValue("presenceSensor") == "present") {    // Only react if Greg is present
    
    	// Actions every time Greg falls asleep at home
    	location.helloHome?.execute(actionSomeoneAsleepAnytime)
        location.helloHome?.execute(actionGregAsleepAnytime)
        
        if(tiffanyAwake.currentValue("switch") == "on") {    // Tiffany is still awake
        
        	// Actions when Greg falls asleep first
        	location.helloHome?.execute(actionSomeoneAsleepFirst)
            location.helloHome?.execute(actionGregAsleepFirst)
            
        } else {    // Tiffany is already asleep
        	
            // Actions when Greg falls asleep last
            location.helloHome?.execute(actionAllAsleep)
            location.helloHome?.execute(actionGregAsleepLast)
            
        }
    }
}



// REACT TO TIFFANY

define heyTiffanyWokeUp(evt) {    // Tiffany woke up!
	if(isTiffanyPresent.currentValue("presenceSensor") == "present") {    // Only react if Tiffany is present
    
    	// Actions every time Tiffany wakes up at home
    	location.helloHome?.execute(actionSomeoneAwakeAnytime)
        location.helloHome?.execute(actionTiffanyAwakeAnytime)
        
        if(gregAwake.currentValue("switch") == "off") {    // Greg is still asleep
        
        	// Actions when Tiffany wakes up first
        	location.helloHome?.execute(actionSomeoneAwakeFirst)
            location.helloHome?.execute(actionTiffanyAwakeFirst)
            
        } else {    // Greg is already awake
        	
            // Actions when Tiffany wakes up last
            location.helloHome?.execute(actionAllAwake)
            location.helloHome?.execute(actionTiffanyAwakeLast)
            
        }
        
    	if(isTiffanySleepingIn.currentValue("switch") == "off") {    // Tiffany is not sleeping in
        	
            // Actions when Tiffany wakes up when NOT sleeping in
            location.helloHome?.execute(actionSomeoneAwakeNotSleepingIn)
            location.helloHome?.execute(actionTiffanyAwakeNotSleepingIn)
        	
        } else {    // Reactions if Tiffany IS sleeping in
        	
            // Actions when Tiffany wakes up WHILE sleeping in
            location.helloHome?.execute(actionSomeoneAwakeSleepingIn)
            location.helloHome?.execute(actionTiffanyAwakeSleepingIn)
            
        }
    }
}



define heyTiffanyFellAsleep(evt) {    // Tiffany fell asleep!
	if(isTiffanyPresent.currentValue("presenceSensor") == "present") {    // Only react if Tiffany is present
    
    	// Actions every time Tiffany falls asleep at home
    	location.helloHome?.execute(actionSomeoneAsleepAnytime)
        location.helloHome?.execute(actionTiffanyAsleepAnytime)
        
        if(gregAwake.currentValue("switch") == "on") {    // Greg is still awake
        
        	// Actions when Tiffany falls asleep first
        	location.helloHome?.execute(actionSomeoneAsleepFirst)
            location.helloHome?.execute(actionTiffanyAsleepFirst)
            
        } else {    // Greg is already asleep
        	
            // Actions when Tiffany falls asleep last
            location.helloHome?.execute(actionAllAsleep)
            location.helloHome?.execute(actionTiffanyAsleepLast)
            
        }
    }
}
