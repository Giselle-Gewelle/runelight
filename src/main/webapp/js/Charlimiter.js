function Charlimiter(inputId) {
	
	var input = $("#" + inputId);
	var maxChars = $("#" + inputId + "Chars").val();
	var output = $("#" + inputId + "Charlimiter");
	
	this.init = function() {
		maxChars = parseInt(maxChars);
		
		if(isNaN(maxChars)) {
			return;
		}
		
	    this.update();
		
		var thisObj = this;
		input.bind("propertychange change paste input", function() {
		    thisObj.update();
		});
	};
	
	this.update = function(watch) {
		var length = 0;
		if(watch !== undefined) {
			length = watch.length;
		} else {
			length = input.val().length;
		}
	    if(length > maxChars) {
	    	input.val(input.val().substring(0, maxChars));
	    	if(watch !== undefined) {
	    		watch = watch.substring(0, maxChars);
	    	}
	    	length = maxChars;
	    }
	    
	    var remaining = maxChars - length;
	    if(remaining === 1) {
	    	output.html("You have 1 character remaining.");
	    } else {
	    	output.html("You have " + remaining + " characters remaining.");
	    }
	};
	
	this.init();
	
}