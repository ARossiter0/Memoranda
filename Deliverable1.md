# Deliverable Information
   > Please include your answers below in a good format so it is easy for me to see. For answers to questions please use these Blockquotes. Make sure you also check the kickoff document for more details. Also make sure this thing is well formatted and the links are links in here. 

## 1: Basic Information (needed before you start with your Sprint -- Sprint Planning)

Note: we had a meeting on Sunday the 18th and this is when we chose the topic, the scrum master and the git master.

**Topic you chose:** 
	>Topic 2 - Teacher Course Planning Tool

**Sprint Number:** 
	>1

**Scrum Master**: 
	>Maayan Janow

**Git Master**: 
	>Tristan Johnson

### Sprint Planning (For Sprint 1-3)
Document your Sprint Planning here. Also check the kickoff document for more details on what needs to be done. This is just the documentation. 
Group meeting on 10/21/2020:
We discussed the user stories the professor gave us and went over the tasks to make sure we understand what is required of us. We discussed our topic and some implementation
details so that we have a better idea of how we want the software to look and function, and how and where we want to put each functionality. 
We decided that except for the UI and bug fixing and understanding memoranda better, we also want to add the ability to add courses and view them in a way that will
make it easy for the user to manage.
Each team member was given a few tasks to create on Taiga and we set up our next meeting, so that everyone have time to go through the user stories and tasks again individually, 
go through memoranada again and discuss additional insights and things that were forgotten, in the next meeting.

**All group memebers attended.

Group meeting 10/23/2020:
During this meeting, we went through memeoranda together and we discussed the UI implementation in greate details, how many tabs we should add, what functionalities in memoranda 
are going to be used and for what purpose. We talked about the topic's requirements and how we are going to execute some of the requirements and where each functionality 
should go, and how.
We came up with some tasks modificatios and we also added some additioanl test cases. 
We went through a complete and comprehensive workflow of how the software is going to work, discussed some bugs (while all functionalities in memoranda are currently
dependant on what project you are on, Events is not, so if you switch between projects, the events you add, remain the same when you switch between
projects, which is not a desired functionality). We also discussed what used stories we want to start with (reverse engineering and bugs fixing) so that it makes it easier for us to move
forward to the next user stories.

**All group members attended. 

**Sprint Goal:** Your Sprint goal
Our sprint goal is to give a user a good understanding of what the software is about, what are the functionalities and abilities of the software, and even allow the
user to execute some simple functionalities, so that the user is confident that we can get the product done efficeintly and include all of the desired functionalities (by adding
all the place holders for future requirements such as tabs and general UI)

**How many User Stories did you add to the Product Backlog:**  3

**How many User Stories did you add to this Sprint:** 2
> Answer the questions below about your Sprint Planning?

**Why did you add these US, why do you think you can get them done in the next Sprint?**
> Your Answer
We feel like we have a good understanding of where we can find those functionalties and we think that it reflects out Sprint goal very well - we want to
demonstrate to the user to the best of our ability, what the software is all about. We believe that not only by modifying the UI, but also by allowing a user
to add a course and view its details, we can achieve our goal very well.

**Why do you think these fit well with your Sprint goal? (details)**
> Your Answer
Our main goal this Sprint is to demonstrate what the software is about and what is its functionality, since we were already given with user stories and tasks to modify the UI 
to match our topic, we wanted to also allow the user to add a course and and view it, so they get even a better idea of the software's functionality

**Do you have a rough idea what you need to do? (if the answer is no then please let me know on Slack)**

> Your Answer
We feel confident that we know what we need to do and how to achieve our goals.


## 2: During the Sprint
> Fill out the Meeting minutes during your Sprint and keep track of things. Update your Quality policies when needed, as explained in the lectures and in the Quality Policy documents on Canvas. 
I would also advise you to already fill out the Contributions section (End of sprint) as you go, to create less work at the end.

### Meeting minutes of your Daily Scrums (3 per week, should not take longer than 10 minutes):
> Add as many rows as needed and fill out the table. (Burndown starts with Sprint 2 and Travis CI starts with Sprint 3, not needed before that). 

| Date  | Attendees  |Meeting notes (very brief)   | Burndown Info (on track, ahead behind is enough) | TravisCI info (does the master pass) | Additional Info  |
|---|---|---|---|--|--|
|10/26/20   | Maayan, Tristan, Anton, Alexander, Ian  | Most of the group already started wroking on some tasks. We talked about implementation of the user stories that are more complex and costructed a plan of action  |   |  |  |
|10/28/20   | Maayan, Tristan, Anton, Alexander, Ian  | At this point had a substantial amout of work done. We discussed our progress and agreed to continue working on the stories given y the professor and leave the ones we added to the end  |   |  |  |
|10/31/20   | Maayan, Tristan, Alexander  | In this meeting we mostly discussed US90 that was a little bt more complex and discussed some implementaion details. We discussed a coulpe of bugs Tristan found in the code and how he fixed them    |   |  |  |

### Meeting Summary:
Overall the meetings were very productive. I always took more then 30 minutes but we feel it this type of communication was important to our success.
During each meeting, we evaluated out progress but also looked at the amout of work we have left as a group and made sure everyone is aware of the dealines and the work that is left,
so that we can finish everything on time and have time for final testing.
> Add rows as needed and add the number how many meetings for each team member:
(this doesn't include 2 additional meetings we had during the sprint planning phase)
   Tristan: 3 
   Maayan: 3
   Ian: 2 
   Alexander: 3
   Andrew: 2 
	

## 3: After the Sprint

### Sprint Review
Answer as a team!

**Screen Cast link**: https://youtu.be/cRkWwU_FK0E

> Answer the following questions as a team. 

**What do you think is the value you created this Sprint?**

> We adapted the UI to be useful for a teacher planning tool. We updated the project panel to be a course panel. We updated the project creation/edit dialog to provide information for a course. We added buttons for lectures,  assignments, and todo lists for teachers, ta’s and graders. We also got the options in the preferences dialog labeled and working. We also fixed the program to make it close correctly. Finally, we provided a series of partial UML diagrams and comments to add some clarity to the codebase. It is now clear that the program is meant to be a course management tool. We have also learned a lot more about how the code works, and how everything is put together. 

**Do you think you worked enough and that you did what was expected of you?**

> We believe that we did work enough, and we accomplished what was expected of us. We gained some familiarity with the memoranda program, and made significant adjustments to it’s user interface. We did fail to complete user story #8, which had to do with updating the title of the program and the splash screen. The team member primarily responsible for this user story did complete the work, but he failed to push it to github, and he dropped the class on the last day of our sprint. 

**Would you say you met the customers’ expectations? Why, why not?**

> For the most part, we believe that we did meet the customer’s expectations for this first sprint. We did make major changes to the user interface. We did not, however, make many significant changes to the backend. We also feel like we could have been more comprehensive with regards to reverse engineering memoranda. However, our main goal for this sprint was to make memoranda look like a course management system, and add some simple functionality for adding courses. We believe we were successful in meeting this goal. 


### Sprint Retrospective

> Include your Sprint retrospective here and answer the following questions in an evidence based manner as a team (I do not want each of your individuals opinion here but the team perspective). By evidence-based manner it means I want a Yes or No on each of these questions, and for you to provide evidence for your answer. That is, don’t just say "Yes we did work at a consistent rate because we tried hard"; say "we worked at a consistent rate because here are the following tasks we completed per team member and the rate of commits in our Git logs."

**Did you meet your sprint goal?**

> Yes. We did meet the sprint goal. We added all of the buttons on the side for lectures, assignments, and todo lists. We also added additional functionality for creating courses with start dates, end dates, exam dates, holidays, and free days, and switching between courses. We also added the ability to view the details of courses. This should clearly demonstrate the purpose of the software to the customer. 

**Did you complete all stories on your Spring Backlog?**

> No. We did not complete user story #8, because the team member primarily responsible for it failed to push his work to github, and dropped the class on the last day of the sprint.  

**If not, what went wrong?**

> The team member primarily responsible for it failed to push his work to github, and dropped the class on the last day of the sprint.  

**Did you work at a consistent rate of speed, or velocity? (Meaning did you work during the whole Sprint or did you start working when the deadline approached.)**

> No. Some of the team members got off to a bit of a slow start, and didn’t begin to push commits until after a few days had passed. We did have a slow start with adjusting some of the technology. Some other team members, for one reason or another, had to be offline or unproductive on the project for a day or two throughout the sprint. Our code frequency slowly ramped up to a peak at around 10/25/2020. Our commits also spiked near the end of the first sprint, with 12 commits on 10/30/2020, and 10 commits on 10/31/2020. A lot of this had to do with how we merged user stories into the development branch. We did work at a consistent rate, as evidenced by our Taiga Sprint #1 Taskboard, but we didn’t start the majority of our commits and merges until later on. 

**Did you deliver business value?**

> Yes. The customer can now envision the purpose of the product with a quick mock-up prototype. If they have concerns about the look and feel of the product, they can now provide feedback and we can adjust accordingly. The user can now add courses and view details about them. 

**Are there things the team thinks it can do better in the next Sprint?**

> Yes. We can improve our git/github workflow to be more clear. After going through sprint 1, it is apparent that there are a few things missing. We also want to commit and push changes more frequently. We should also constantly merge user stories into development when they are completed, rather than waiting until the end of the sprint to merge in everything at once. We also need to make sure that we can find times for every team member to meet and communicate. Finally, we also need to better balance the workload, especially with collaborating and elaborating on user stories. 

**How do you feel at this point? Get a pulse on the optimism of the team.**

> We feel optimistic, yet apprehensive at the same time. Several of us worked over the ~10 hour a week guideline that was suggested in the kickoff document, and for the most part we have only changed the surface level user interface elements. We need to start working more on backend items and particularly transitioning to json. We feel, however, that we will be able to finish our project. 

### Contributions:

> In this section I want you to point me to your main contributions (each of you individually). Some of the topcs are not needed for the first deliverables (you should know which things you should have done in this Sprint, if you don't then you have probably missed something):

#### Tristan Johnson:
  **Links to GitHub commits with main code contribution (up to 5 links) - all Sprints:

    - https://github.com/amehlhase316/Leipzig-2/commit/a8b2e931a19857672ade0f548aa394caabee6b12
    - https://github.com/amehlhase316/Leipzig-2/commit/e16bce5c0305317c1f31ee85e7110373266dcc9b
    - https://github.com/amehlhase316/Leipzig-2/commit/4d463f7211064159883f3972d65325f556524b5f
    - https://github.com/amehlhase316/Leipzig-2/commit/f65024026766bbf7bc6a412caab4a2fa5a7be14b
    - https://github.com/amehlhase316/Leipzig-2/commit/baac25749365d9152d9f75600adc1855b75fc3a0
 
 **What was your main contribution to the Quality Policy documentation?:

    - I wrote the first draft of the github workflow. 

#### Ian Swanlund
  **Links to GitHub commits with main code contribution (up to 5 links) - all Sprints:

    - https://github.com/amehlhase316/Leipzig-2/commit/7d20b25dc558afab6efe9db70c279693611a9def
    - https://github.com/amehlhase316/Leipzig-2/commit/30c80e3ff3daef7754c1f140f611522409f71b40
    - https://github.com/amehlhase316/Leipzig-2/commit/1f4a958691b31a206cc8376e1f21ee6d8ee5ed7a
    - https://github.com/amehlhase316/Leipzig-2/commit/634b4455223be4e57c9e2b5753aa0b95335398fb
    - https://github.com/amehlhase316/Leipzig-2/commit/54beb6b964d874bd33768e6ee23796d1bd6d0d92

 **What was your main contribution to the Quality Policy documentation?:

    - Reviewing policys concerning user story commits.

#### Maayan Janow:
  **Links to GitHub commits with main code contribution (up to 5 links) - all Sprints:

    - https://github.com/amehlhase316/Leipzig-2/commit/85ab37122bef847097129e71d0038b1b610a8956
    - https://github.com/amehlhase316/Leipzig-2/commit/696c0b7cdf9cd1910a4f0102fe7edcfa8ac432e2
    - https://github.com/amehlhase316/Leipzig-2/commit/2b201ebdce15b345b958c9b6c243ee866c6282ff
    - https://github.com/amehlhase316/Leipzig-2/commit/a04e82daebc3333e42e2c1e3565800d46ed4b5ee
    - https://github.com/amehlhase316/Leipzig-2/commit/14bc78f1df79131fe699923003df44c29f858c49
 
 **What was your main contribution to the Quality Policy documentation?:

    - I modified the whole draft and added things that were missing, changes a lot of the initials suggestions and siplified the document. 

#### Alexander Rossiter:
  **Links to GitHub commits with main code contribution (up to 5 links) - all Sprints:

    - https://github.com/amehlhase316/Leipzig-2/commit/0ce5a8b7702f2e716f287b83dc6e66aa47698f6c
    - https://github.com/amehlhase316/Leipzig-2/commit/45c050664ea372c022781dace81d6b335f8e5ff0
    - https://github.com/amehlhase316/Leipzig-2/commit/f7339638092d4f5956597b01f5c5d7c21bbb9cda
  
 
 **What was your main contribution to the Quality Policy documentation?:

    - I modified the whole draft and added things that were missing, changes a lot of the initials suggestions and siplified the document. 


#### Tristan Johnson -- replace this with your name:
  **Links to GitHub commits with main code contribution (up to 5 links) - all Sprints:

    - link1
    - link2

   **GitHub links to your Unit Tests (up to 3 links) -- Sprint 2 and 3:

    - link1
    - link2

  **GitHub links to your Code Reviews (up to 3 links) -- Sprint 2 and 3:

    - link1
    - link2

  **How did you contribute to Static Analysis -- Sprint 3:

    - link1
    - link2
 
 **What was your main contribution to the Quality Policy documentation?:

    - info
  
## 4: Checklist for you to see if you are done
- [x ] Filled out the complete form from above, all fields are filled and written in full sentences
- [x ] Read the kickoff again to make sure you have all the details
- [x ] User Stories that were not completed, were left in the Sprint and a copy created
- [x ] Your Quality Policies are accurate and up to date
- [ ] **Individual** Survey was submitted **individually** (create checkboxes below -- see Canvas to get link)
  - [ ] Team member 1
  - [ ] Team member 2
- [x ] The original of this file was copied for the next Sprint (needed for all but last Sprint where you do not need to copy it anymore)
  - [x ] Basic information (part 1) for next Sprint was included (meaning Spring Planning is complete)
  - [x ] All User Stories have acceptance tests
  - [x ] User Stories in your new Sprint Backlog have initial tasks which are in New
  - [x ] You know how to proceed
