(function () {
	
	var app = angular.module("TicketingApp", []);
	
	app.controller("TicketingCtrl", [ "$http", "$scope", function($http, $scope) {
		
		this.quickMessages = [
			{
				"title": "Noob / newb",
				"text": "Thank you for contacting {company}.\n\n" +
						"I can certainly understand your concern and how upset this has made you.\n\n" +
						"The term \"Noob\" derives from the word \"Newbie\" or \"New Person\". People will commonly use it as an insult but remember that its direct meaning simply means \"New Person\".\n\n" +
						"Although this may not be the case for yourself and it has been meant as an insult you should simply place that player on your ignore list or ignore the comment yourself.\n\n" +
						"Remember, we were all noobs at one point, even us {company} staff, even the player directing the term. Do not let it affect you!\n\n" +
						"I hope this helps resolve your problem.\n\n"
			},
			{
				"title": "Invalid offense",
				"text": "Thank you for your message regarding your recent offense.\n\n" + 
						"After looking into this situation, it seems as though no offense has taken place.\n\n" + 
						"We sincerely apologize for any inconvenience and have removed the black marks from your account.\n\n" + 
						"We hope that this helps and that you can place your absolute trust in {company} in the future.\n\n" + 
						"Happy Gaming! ^_^\n\n"
			}
		];
		
		this.quickMessage = "";
		this.message = "";
		
		this.error = false;
		
		var messageCharlimiter = new Charlimiter("inputMessage");
		
		this.selectQuickMessage = function() {
			if(this.quickMessage === null) {
				this.quickMessage = "";
			} else {
				var text = "Dear {name},\n\n" + this.quickMessage.text + "Please always refer to the {game} Knowledge Base to find the answer to your queries quicker.\n\nYours Sincerely,\n{username}";
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
			
			this.message = this.message.trim();
			
			if(this.message == "") {
				this.error = true;
			}
			
			if(!this.error) {
				$("#messageForm").submit();
			}
		};
		
	}]);
	
})();