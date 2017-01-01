function Location() {
	this.items	= {
	'0':{1:'儿童基因',2:'肥胖基因',3:'运动基因',4:'乳糖酶基因'},
	'0,1':{5:'智商基因'},
	'0,1':{6:'情商基因'},
	'0,1':{7:'艺术基因'}
	}
}

Location.prototype.find	= function(id) {
	if(typeof(this.items[id]) == "undefined")
		return false;
	return this.items[id];
}

Location.prototype.fillOption	= function(el_id , loc_id , selected_id) {
	var el	= $('#'+el_id); 
	var json	= this.find(loc_id); 
	if (json) {
		var index	= 1;
		var selected_index	= 0;
		$.each(json , function(k , v) {
			var option	= '<option value="'+k+'">'+v+'</option>';
			el.append(option);
			
			if (k == selected_id) {
				selected_index	= index;
			}
			
			index++;
		})
		//el.attr('selectedIndex' , selected_index); 
	}
	el.select2("val", "");
}