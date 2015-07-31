function updatePage(formName) {
	var form = null;
	
	if(formName == "topForm") {
		form = document.topForm;
	} else {
		form = document.bottomForm;
	}
	
	var newPage = parseInt(form.page.value);
	var pageCount = parseInt(form.pageCount.value);
	var currentPage = parseInt(form.currentPage.value);
	
	if(isNaN(currentPage)) {
		currentPage = 1;
	}
	
	if(isNaN(newPage) || isNaN(pageCount) || newPage > pageCount || newPage < 1) {
		form.page.value = currentPage;
	} else {
		form.submit();
	}
}