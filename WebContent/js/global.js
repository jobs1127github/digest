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
/**
 * 全选/全不选 check box，全选当前页的check box
 * node就是当前点击的check box，是否checked：true表示选中，false表示没选中。
 */
function checkAll(node) {
	/***
	 * 根据元素名拿到所有的元素，是一个数组
	 */
	var collNodes = document.getElementsByName("checks");
	//alert(collNodes.length);
	/**
	 * 循环迭代该数组，把每个check box都拿出来，修改他们的checked属性，和当前点击的check box 属性一致即可。
	 * 当前点击的check box选中，则其他的所有check box就选中。
	 */
	for ( var i = 0; i < collNodes.length; i++) {
		collNodes[i].checked = node.checked;
	}
	//alert(node.checked);
}
/***
 * 当全选后，再次去点击check box
 */
function doublecheck() {
	var collNodes = document.getElementsByName("checks");
	var b = 0;
	var tops = document.getElementById("checktop");
	for (var i = 0; i < collNodes.length; i++) {
		if (collNodes[i].checked) {
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