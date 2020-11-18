### Quality Policy
> Describe your Quality Policy in detail for this Sprint (remember what I ask you to do when I talk about the "In your Project" part in the lectures and what is mentioned after each assignment (in due course you will need to fill out all of them, check which ones are needed for each Deliverable). You should keep adding things to this file and adjusting your policy as you go.

**GitHub Workflow** (due start Sprint 1)

NOTE: For a cleaner version, check the docx in the leipzig2_git slack channel

--When creating a new user story (in sprint planning cycle)--
•git pull <development branch>
•git checkout <development branch>
•git branch (check that the user story branch has not already been created on the remote repository, if it has been done, just check out that branch. If not, do the steps below.)
•git branch <user story branch>
•git checkout <user story branch>
•git push origin <user story branch>


--When resuming work on a user story--
•git pull <user story branch>
•git checkout <user story branch>
	you can now start working on your tasks


--When temporarily finished working on user story--
•git checkout <user story branch>
•git add <all files that you have changed>
•git commit -m “Good commit message”
•git fetch <user story branch>
•git merge origin/<user story branch>
•Check for any merge conflicts, make sure that the result make sense and won’t unduly confuse the other team members!
•git push origin <user story branch>


--When user story is finished--
•git pull <development branch> - you always have to make sure you pull again because changes were probably made to the dev branch since your last pull!
•git checkout <user story branch> 
•git add <file.java>
•git commit -m “example: US138 Task158 Update file.java with env variables details”
•git merge <development> - you always want to merge development into your branch, because it is very likely that while you were working on your code, some other branches were merged into dev and you no longer have the most current version
•Resolve any conflicts if any (if each of us works on a user story, there shouldn’t be any conflicts)
•IMPORTANT: test the program again and make sure your new addition works with the latest version of the development branch and that your addition didn’t break anything (remember, you may not have worked on the most current version). If the program doesn’t work as expected, and you make changes, repeat all stages above after you are done.
•git push origin <development>
•On github, make pull request merging <user story branch> into <development branch>
•Another team member does detailed code review and determines if the code is ready to be merged. If so, then they approve the pull request with a good code review message. Development branch should always be stable with newest working/tested copy of your software. 
•the git master will now merge the user story branch to development (and now development branch is up to date for someone else to pull branch out of, and make changes) 


--Removing user story branch on remote repository--
•ONLY GIT MASTER DOES THIS, AND ONLY AFTER SPRINT IS OVER AND ALL USER STORY BRANCHES HAVE BEEN SUCCESSFULLY MERGED INTO DEV!!!!!
•git push origin --delete <user story branch>


--Removing local branch--
•DO THIS AT YOUR OWN PERIL; YOU WILL LOSE YOUR LOCAL WORK ON THE BRANCH. SHOULD PROBABLY ONLY DO THIS AFTER SPRINT IS OVER AND ALL USER STORY BRANCHES HAVE BEEN SUCCESSFULLY MERGED INTO DEV!
•git branch -d <user story branch>
•git remote prune origin


--When ready to merge development branch into main branch git master should--
•git pull <main branch>
•Resolve any conflicts
•git checkout <development branch>
•git merge <main branch> 
•Resolve any conflicts
•On github, make pull request merging <development branch> into <main branch>. At least 2 other team members must do detailed code review and test the code. If all have approved with a good message, then the git master can merge into main. Main branch should always be in a deployable state. 



**Unit Tests Blackbox** (due start Sprint 2)
  > Your Blackbox testing policy:
	Each team memeber must add at least 4 Blackbox testing every sprint.
	Testing will be done at the end of each User Story, as possible (GUI components are usually impossible to test via unit testing)
	If a group member finds that during the testing phase they see fit to add a unit testing to a function another member wrote, they are allowed to do so.
	If a member works monstly on GUI based user stories and cannot find a way to do unit testing, the member can choose any other functionality in from memoranda to test
  	  

 **Unit Tests Whitebox** (due start Sprint 2)
  > Your Whitebox testing policy 
	Each team memeber must add at least 4 Whitebox testing every sprint.
	Testing will be done at the end of each User Story, as possible (GUI components are usually impossible to test via unit testing)
	If a group member finds that during the testing phase they see fit to add a unit testing to a function another member wrote, they are allowed to do so.
	If a member works monstly on GUI based user stories and cannot find a way to do unit testing, the member can choose any other functionality in from memoranda to test
  	

**Code Review** (due start Sprint 2)
  > Your Code Review policy   
	Each team meber will conduct a developer code review and will fill out the checklist before creating a pull request
	After the pull request, the member testing the code will conduct a reviewer code review and will fill out the check list
  > Include a checklist/questions list which every developer will need to fill out/answe when creating a Pull Request to the Dev branch. 
	Coding Standards (CG)
	- [ ] All methods that have been created/changed have a Javadoc comment present and filled in with a description, explanations for parameters, and explanations for return values. 
	- [ ] New constants and Enums are in all CAPS
	- [ ] New class names are upper CamelCase
	- [ ]New variables, parameters, and method names are lower camelCase. 
	- [ ] New attributes are private
	- [ ] New literal values, except loop indices starting at 0 or 1 are declared as constants
	- [ ] All new code with {} appear with { at the end of a line, and } on its own line
	- [ ] Indentation for all new statements is tabbed. 
	- [ ] All new complex statements use explicit {} even if the body is a single line. 

	Code Smells
	- [ ] There is no significant duplicate code. 
	- [ ] There are no new large classes.
	- [ ] There are no new lazy classes. 
	- [ ] There are no new switch statements. 
	- [ ] There are no data clumps.
	- [ ] New methods have 3 or fewer parameters. 
	- [ ] New methods are not longer than 100 lines (with whitespace included).
	- [ ] Identifiers are shorter than 20 characters. 
	- [ ] Identifiers are longer than 3 characters. 
	
  > Include a checklist/question list which every reviewer will need to fill out/anser when conducting a review, this checklist (and the answers of course) need to be put into the Pull Request review.
	Coding Standards (CG)
	- [ ] All methods that have been created/changed have a Javadoc comment present and filled in with a description, explanations for parameters, and explanations for return values. 
	- [ ] New constants and Enums are in all CAPS
	- [ ] New class names are upper CamelCase
	- [ ]New variables, parameters, and method names are lower camelCase. 
	- [ ] New attributes are private
	- [ ] New literal values, except loop indices starting at 0 or 1 are declared as constants
	- [ ] All new code with {} appear with { at the end of a line, and } on its own line
	- [ ] Indentation for all new statements is tabbed. 
	- [ ] All new complex statements use explicit {} even if the body is a single line. 

	Code Smells
	- [ ] There is no significant duplicate code. 
	- [ ] There are no new large classes.
	- [ ] There are no new lazy classes. 
	- [ ] There are no new switch statements. 
	- [ ] There are no data clumps.
	- [ ] New methods have 3 or fewer parameters. 
	- [ ] New methods are not longer than 100 lines (with whitespace included).
	- [ ] Identifiers are shorter than 20 characters. 
	- [ ] Identifiers are longer than 3 characters.

**Static Analysis**  (due start Sprint 3)

One Time-------------------------------------
  -Create a StaticAnalyis branch off of development
    git checkout development
    git branch StaticAnalysis
  -Add the checkstyle and spotbugs info to build.gradle. Copy and paste from Assignment 4. 
  -Run the program to make sure that checkstyle and spotbugs reports are correctly generated. 
  -Then push the program to github
    git push origin StaticAnalysis
  -IF travis CI has already been added, go to https://travis-ci.com/ and make sure that the newest build on the StaticAnalysis branch passes. 
  -Create a pull request of StaticAnalysis into development. Someone should then review that Travis CI does work on the StaticAnalysis branch (if travis ci is already enabled in it), and accept the request. 

Before Every Commit----------------------
  -Run gradle build before every commit. Make sure that there are no checkstyle violations or spotbug violations in files that you have added.
     gradle build
     Check spotbug and checkstyle reports
  -If there are ckeckstyle or spotbug violations in any files that you have added, conduct a code review (following the QualityPolicy.md guidelines) of the offending methods. If, upon review, the violation causes some runtime error to occur, create a blackbox unit test that will return true only if the violation is not present, and false if it is. 

After Every Merge/Pull--------------------
  -Also run gradle build after every merge/pull. Make sure that there are no checkstyle violations or spotbug violations in files that you have added. 
     gradle build
     Check spotbug and checkstyle reports
  -If there are ckeckstyle or spotbug violations in any files that you have added, conduct a code review (following the QualityPolicy.md guidelines) of the offending methods. If, upon review, the violation causes some runtime error to occur, create a blackbox unit test that will return true only if the violation is not present, and false if it is. 


**Continuous Integration**  (due start Sprint 3)

One Time--------------------------------------
  -Create a  TravisCI branch off of development
     git checkout development
     git branch TravisCI
  -Setup Travis CI on a local version of the TravisCI branch of the project. 
     Add .travis.yml to root of the project
     gradle wrapper
  -Then push the branch to github 
     git push origin TravisCI
  -Ensure that it works. Go to https://travis-ci.com/, and make sure that the TravisCI build passes. 
     Create a pull request of TravisCI into development. Someone should then review that Travis CI does work on the TravisCI branch, and accept the request. 

After every push------------------------------
  -Go to https://travis-ci.com/, and make sure that the most recent build of the branch that you pushed passes. 
  -If not, do a code review of the files that were changed since the last push, and any failing unit tests. 

After every pull request accept-----------
  -Go to https://travis-ci.com/, and make sure that the most recent build of the branch that you pulled changes into passes.
  -If not, do a code review of the files that were changed in the branch pulled in, and any failing unit tests. 
