function Location(classify) {
	var oneclassify="{'0':{";
	var towclassify="";
	for(var i=0;i<classify.length;i++){
		if(i==classify.length-1){
			oneclassify=oneclassify+classify[i].id+":"+"'"+classify[i].name+"'"+"},\n";
			if(null==classify[i].row){
			}else{
				for(var j=0;j<classify[i].row.length;j++){
					if(j==0){
						if(classify[i].row.length>1){
							towclassify=towclassify+"'0,"+classify[i].id+"':{"+classify[i].row[j].id+":"+"'"+classify[i].row[j].claName+"'"+",";
						}else{
							towclassify=towclassify+"'0,"+classify[i].id+"':{"+classify[i].row[j].id+":"+"'"+classify[i].row[j].claName+"'"+"}\n";
						}
					}else{
						if(j==classify[i].row.length-1){
							towclassify=towclassify+classify[i].row[j].id+":"+"'"+classify[i].row[j].claName+"'"+"}\n";
							}else{
							towclassify=towclassify+classify[i].row[j].id+":"+"'"+classify[i].row[j].claName+"'"+",";	
							}
					}
				}
			}
		}else{
		oneclassify=oneclassify+classify[i].id+":"+"'"+classify[i].name+"'"+",";
			if(null==classify[i].row){
			}else{
				for(var j=0;j<classify[i].row.length;j++){
					if(j==0){
						if(classify[i].row.length>1){
							towclassify=towclassify+"'0,"+classify[i].id+"':{"+classify[i].row[j].id+":"+"'"+classify[i].row[j].claName+"'"+",";
						}else{
							towclassify=towclassify+"'0,"+classify[i].id+"':{"+classify[i].row[j].id+":"+"'"+classify[i].row[j].claName+"'"+"}\n";
						}
					}else{
						if(j==classify[i].row.length-1){
						towclassify=towclassify+classify[i].row[j].id+":"+"'"+classify[i].row[j].claName+"'"+"},\n";
						}else{
						towclassify=towclassify+classify[i].row[j].id+":"+"'"+classify[i].row[j].claName+"'"+",";	
						}
					}
				}
			}
		}
		
	}
	var testJson = eval("(" + oneclassify+towclassify+"}"+ ")"); 
	this.items=testJson;
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