/**
 * 获取tree实心节点
 * 
 * @param id
 * @returns {Array}
 */
function getSolidNode(id) {
	var nodes = [];
	$("#" + id).find('.tree-checkbox2').each(function() {
		var node = $(this).parent();
		nodes.push($.extend({}, $.data(node[0], 'tree-node'), {
			target : node[0],
			checked : node.find('.tree-checkbox').hasClass('tree-checkbox2')
		}));
	});
	return nodes;
}

/**
 * 只能选择职务
 * 
 * @param treeID
 */
function deleteTreeNode(treeID) {
	var roots = $('#' + treeID).tree('getRoots'), children, i, j;
	for (i = 0; i < roots.length; i++) {
		children = $('#' + treeID).tree('getChildren', roots[i].target);
		for (j = 0; j < children.length; j++) {
			var node = $('#' + treeID).tree('find', children[j].id);
			// 如果 类型 为 不为 2 并且是子节点 就删除
			var isCompany = node.attributes.ouIsCompany;
			var left = $('#' + treeID).tree('isLeaf', node.target);
			if (isCompany != 2 && (left)) {
				$('#' + treeID).tree('remove', node.target);
			}
		}
	}
}