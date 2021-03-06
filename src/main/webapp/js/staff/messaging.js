(function () {
	
	var app = angular.module("MessagingApp", []);
	
	app.controller("MessagingCtrl", [ "$http", "$scope", function($http, $scope) {
		
		this.quickMessages = [
			{
				/*
				 * This is not really valid here (since it's a reply), just an example!
				 */
				"title": "Noob / newb",
				"text": "Thank you for contacting {company}.\n\n" +
						"I can certainly understand your concern and how upset this has made you.\n\n" +
						"The term \"Noob\" derives from the word \"Newbie\" or \"New Person\". People will commonly use it as an insult but remember that its direct meaning simply means \"New Person\".\n\n" +
						"Although this may not be the case for yourself and it has been meant as an insult you should simply place that player on your ignore list or ignore the comment yourself.\n\n" +
						"Remember, we were all noobs at one point, even us {company} staff, even the player directing the term. Do not let it affect you!\n\n" +
						"I hope this helps resolve your problem.\n\n" +
						"Please always refer to the {game} Knowledge Base to find the answer to your queries quicker."
			}
		];
		
		this.quickMessage = "";
		
		this.title = "";
		this.message = "";
		
		this.error = false;
		
		var messageCharlimiter = new Charlimiter("inputMessage");
		
		this.selectQuickMessage = function() {
			if(this.quickMessage === null) {
				this.quickMessage = "";
			} else {
				var text = "Dear {name},\n\n" + this.quickMessage.text + "\n\nYours Sincerely,\n{username}";
				text = text.replace(/{company}/g, company);
				text = text.replace(/{game}/g, game);
				text = text.replace(/{username}/g, username);
				text = text.replace(/{name}/g, receiverName);
				this.message = text;
				messageCharlimiter.update(this.message);
			}
		};
		
		this.submit = function() {
			this.error = false;
			
			this.title = this.title.trim();
			this.message = this.message.trim();
			
			if(this.title == "" || this.message == "") {
				this.error = true;
			}
			
			if(!this.error) {
				$("#messageForm").submit();
			}
		};
		
	}]);
	
})();