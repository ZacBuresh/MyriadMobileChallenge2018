# MyriadMobileChallenge2018
This is my submission for the Myriad Mobile challenge of 2018.

Author: Zachary Buresh

Date: 12/12/2018


Files:

  Java:
  
      LoginActivity.java, EventList.java, EventDetails.java, Event.java, EvenListAdapter.java,
      
      Speaker.java, SpeakerListAdapter.java, User.java, EventService.java, PostService.java,
      
  XML:
  
      activity_event_details.xml, activity_event_list.xml, activity_login.xml, event_list_item.xml
      
      toolbar_menu.xml   
      

Description:

  -Asks the user to login/register if no credentials are found in shared preferences.
  
  -Posts their entered credentials using retrofit to retrieve an authorization token.
  
  -Gets all the event data using retrofit to store the data in separate Event objects.
  
  -Displays all events including their title, date, and image in a recycler view.
  
  -When an event is clicked, retrofit is used to get data from that specific event.
  
  -All event details are displayed along with its speaker(s) details.
  
  -The "speakers" attribute in each event object determines which speakers are retrieved.
  
  -Retrofit is used for the last time to retrieve speaker details and displaying them in a recycler view.
  
Extra Features:

  -A "back" button is implemented on the Event Details screen to let the user navigate back to the EventList Activity.
  
  -The overflow icon on the EventList Activity allows the user to logout, which starts the Login Activity.
  
  -If the user closes the app while logged in, when they reopen the app, shared preferences retrieves their credentials.
  
  -If the user closes the app while NOT logged in, the app will start at the login screen when it is reopened.
