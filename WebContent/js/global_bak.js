var flags = false;
function shows(node) {
	var onewsa = document.getElementById(node);
	if (!flags) {
		onewsa.style.display = "block";
		flags = true;
	}
}
function closes(node) {
	var onewsb = document.getElementById(node);
	if (flags) {
		onewsb.style.display = "none";
		flags = false;
	}
}
/*
 * 全选 check box，全选当前页的check box
 */
function checkAll(node) {
	var collNodes = document.getElementsByName("checks");
	//alert(collNodes.length);
	for ( var x = 0; x < collNodes.length; x++) {
		collNodes[x].checked = node.checked;
	}
}
function doublecheck() {
	var collNodes = document.getElementsByName("checks");
	var b = 0;
	var tops = document.getElementById("checktop");
	for ( var x = 0; x < collNodes.length; x++) {
		if (collNodes[x].checked) {
			tops.checked = false;
		} else {
			b++;
		}
	}
	if (b == 0) {
		tops.checked = collNodes[0].checked;
	}
}
function checkAll1(node) {
	var collNodes = document.getElementsByName("ck");
	for ( var x = 0; x < collNodes.length; x++) {
		collNodes[x].checked = node.checked;
	}
}
function doublecheck1() {
	var collNodes = document.getElementsByName("ck");
	var b = 0;
	var tops = document.getElementById("checktop");
	for ( var x = 0; x < collNodes.length; x++) {
		if (collNodes[x].checked) {
			tops.checked = false;
		} else {
			b++;
		}
	}
	if (b == 0) {
		tops.checked = collNodes[0].checked;
	}
}