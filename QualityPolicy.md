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
  > Your Blackbox testing policy 

 **Unit Tests Whitebox** (due start Sprint 2)
  > Your Whitebox testing policy 

**Code Review** (due start Sprint 2)
  > Your Code Review policy   

  > Include a checklist/questions list which every developer will need to fill out/answe when creating a Pull Request to the Dev branch. 

  > Include a checklist/question list which every reviewer will need to fill out/anser when conducting a review, this checklist (and the answers of course) need to be put into the Pull Request review.

**Static Analysis**  (due start Sprint 3)
  > Your Static Analysis policy   

**Continuous Integration**  (due start Sprint 3)
  > Your Continuous Integration policy
