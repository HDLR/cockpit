if (!String.prototype.trim) {
    String.prototype.trim = function () {
        return this.replace(/^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g, '');
    };
}
String.prototype.format = function () {
    var args = arguments;
    return this.replace(/\{(\d+)\}/g,
        function (m, i) {
            return args[i];
        });
}
var $style = {
    base:'oil',
    gas:{
        color:'#38b8ff'
    },
    oil:{
        color:'#59ebe8'
    },
    merge:function(opt, style){
        style = style || 'bas';
        if(typeof(style)=="string"){
            style = $style[style];
            if(style == null)
                style = {};
        }
        var option = $.extend(true, {}, style, opt);
        return option;
    },
    setTheme:function(g){
        if(g!='gas') return;
        var c = $style[g].color;
        $style.bar.xAxis.axisLine.lineStyle.color = c;
        $style.bar.yAxis.axisLine.lineStyle.color = c;
    },
    bas:{
        legend: {  //图例组件，颜色和名字
            show:true,
            type:'scroll',
            bottom : '2%',
            itemGap: 6, //图例每项之间的间隔
            itemWidth: 16, //图例宽度
            itemHeight: 8, //图例高度
            textStyle: {
                color:'#fff',
                fontFamily: '微软雅黑',
                fontSize: 10,
            },
            pageButtonItemGap:1,
            pageTextStyle:{
                color:'#fff',
            },
        },
    },
    pie:{
    },
    bar:{
        grid:{
            top:'10%',
            bottom:'5%',
        },
        legend: {  //图例组件，颜色和名字
            show:true,
            itemGap: 12, //图例每项之间的间隔
            itemWidth: 8,
            itemHeight: 8,
            textStyle: {
                fontStyle: 'normal',
                fontFamily: '微软雅黑',
                fontSize: 8,
            }
        },
        tooltip:{},
        xAxis: {
            type: 'category',
            axisLine: {
                lineStyle: {
                    color: '#38b8ff'
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#ffffff'
                }
            }
        },
        yAxis: {
            splitLine: {
                show: false
            },
            type: 'value',
            splitLine: {
                show: false
            },
            axisLine: {
                lineStyle: {
                    color: '#38b8ff'
                }
            },
            axisLabel: {
                textStyle: {
                    color: '#ffffff'
                }
            }
        },
    }
};
var $chart = {
    xtime:function(d){
        for(var i = 0, l = d.length; i<l; i++){
            var a = d[i].split('-');
            if(a.length == 3)
                d[i] = a[1] + '-' + a[2] + '\n' + a[0];
        }
        return d;
    },
    init:function(id, option, style){
        option = $style.merge(option, style);
        var myChart = echarts.init($(id)[0]);
        myChart.setOption(option);
        return myChart;
    },
    ico:function(color, type){
        return '<span style="display:inline-block;margin-right:5px;border-radius:10px;width:10px;height:10px;background-color:'+color+';"></span>';
    }
};

function gourl(url){
    location.href = url;
}
function getdata(url, o){
    url = __host + '/static/data' + url;
    if(typeof(o) == 'function'){
        o = {success:o};
    }
    o = $.extend({
        url:url,
        method:'get',
        error:function(jq,state){
            console.log(url+' :ajax error,'+jq.status);
        }
    }, o);
    $.ajax(o);
}
function getdatax(url, fn, opt){
    if(!window._store) window._store = {};
    var o = $.extend({
        cid: fn.name,
        cfn:fn,
        cdata:[],
        sid:fn.name+'_comb',
        schange:changeSelect,
        sidx:2,
        sdata:[],
        svalue:'',
    }, opt);
    getdata(url, function(data){
        initStore(data, o); //init store
        creatSelect(o.sdata, o.sid, o.svalue); //draw select
        fn(o.cdata, o.svalue);    //draw chart
    });
}
function initStore(data, o){
    if(o.sidx>0){
        o.cdata = initStoreData(data, o.sidx);
        o.sdata = o.cdata.index;
        o.svalue = o.cdata.index[o.cdata.index.length - 1];
    }else{
        o.cdata = data;
    }
    _store[o.cid] = o; //save
}
function initStoreData(data, idx){
    var ds = {legend:[],xData:[],data:{}};
    ds.legend = data[0];
    ds.xData = data[1];
    var res = new Map();
    ds.index = data[idx].filter(function(a){
        return !res.has(a) && res.set(a, 1);
    });

    ds.index.forEach(function(x){
        var arr = [];
        for(var i = 3; i <data.length; i++){
            arr.push([]);
        }
        ds.data[x] = arr;
    });
    var j = -1;
    for(var i = 0; i <data.length; i++){
        if(i == 0 || i == 1 || i == idx)
            continue;
        j++;
        data[i].forEach(function(o, m){
            var key = data[idx][m];
            if(ds.data[key])
                ds.data[key][j].push(data[i][m]);
        });
    }
    return ds;
}
function changeSelect(sel){
    var id = sel.id, val = sel.value, o = _store[id], fn = o.cfn;
    fn(o.cdata, val);
}
function creatSelect(data,id,shouValue,mo){
    var str = '<select id= "select" class="selectpicker form-control"  style="background-color: rgba(23,32,45,0);width: 70px;height: 20px;text-align: center;border-radius:10px;border: 1px solid #37bff4;" onchange = "'+mo+'(this.value)">';
    for (var i = 0; i < data.length; i++) {
        if (data[i] == shouValue) {
            str = str+'<option value = "'+data[i]+'" selected = "selected">'+data[i]+'</option>';
        }else{
        	str = str+'<option value = "'+data[i]+'">'+data[i]+'</option>';
        }	
    }
    str = str+'</select>';
    var tbody=window.document.getElementById(id);
    tbody.innerHTML = str;
}

var RollingPlay = {
    ts : 5,
    uri:['market/finance','pattern/gasIndex','pattern/gasStore','statecn/index','statecn/opinions','statecn/gasIndex','statecn/gasSecurity','pattern/index','pattern/gasMarket'],
    rollPlayInit:function(id){
        this.timespan = this.ts;
        this.playing = "false" === localStorage.RollingPlay_ply ? false : true;
        this.pan = $('#'+id);
        this.num = this.pan.find(".rollingTime");
        this.numbx = this.pan.find(".rollingPlay");
        this.btnbx = this.pan.find(".rollingStop");
        this.pan.click(function(){
            RollingPlay.playing = !RollingPlay.playing;
            localStorage.RollingPlay_ply = RollingPlay.playing;
            if(!RollingPlay.playing){
                RollingPlay.numbx.hide();
                RollingPlay.btnbx.show();
            }else{
                RollingPlay.btnbx.hide();
                RollingPlay.numbx.show();
                RollingPlay.timespan = RollingPlay.ts;
                RollingPlay.autoRolling();
            }
        });
        if(this.playing)
            this.autoRolling();
        else{
            this.numbx.hide();
            this.btnbx.show();
        }
    },
    autoRolling:function(){
        if(RollingPlay.playing){
            RollingPlay.doRolling();
            setTimeout(RollingPlay.autoRolling, 1000);
        }
    },
    doRolling:function(){
        this.timespan--;
        this.num.html(this.timespan);
        if(this.timespan == 0){
            this.goNext();
            this.timespan = this.ts;
        }
    },
    goNext:function(){
        var u = location.pathname.split('/'), l = u.length - 1, curr = u[l-1]+'/'+u[l], uri = [], b = false;
        $(".barbtn").each(function(i){
            var s = $(this).attr('onclick').split("'");
            if(!s || s.length !=3) return true;
            uri.push(s);
        });
        for(var i = 0; i<uri.length; i++){
            var s = uri[i];
            if(s[1].indexOf('/')>-1){
                if(s[1].indexOf('../') == 0)
                    b = s[1] == '../' + curr;
                else
                    b = s[1] == curr;
            }else{
                b = s[1] == u[l];
            }
            if(b){
                if(i <= uri.length - 2 && curr !='pattern/gasStore' && curr !='pattern/gasMarket' && curr != 'statecn/gasSecurity'){
                    $(".barbtn").eq(i+1).click();
                    break;
                }else{
                    var x = this.uri.indexOf(curr);
                    var nextu = x<=this.uri.length - 2 ? this.uri[x+1] : this.uri[0];
                    gourl(__host + '/module/' + nextu);
                }
            }
        }
    }
};
function RollingPlayNew(menu, g, curm){
    RollingPlay.uri = menu;
    RollingPlay.group = g;
    RollingPlay.curr = curm;
    RollingPlay.goNext = function(){
        var curr = location.href.replace(__host,''), uri = this.uri, nextu = '', b = false, i = -1, tp = '', curm = this.curr;
        if(curm && uri[curm] && (i = uri[curm].indexOf(curr))>-1){
            b = true;
            tp = curm;
        }else{
            for(var m in uri){
                i = uri[m].indexOf(curr);
                if(i>-1){
                    b = true;
                    tp = m;
                    break;
                }
            }
        }
        if(b){
            if(i<uri[tp].length-1){
                i++;
            }else{
                i = 0;
                var j = this.group.indexOf(tp) + 1, l = this.group.length;
                tp = this.group[j % l];
            }
            gourl(__host + this.uri[tp][i]);
        }
    };
    RollingPlay.rollPlayInit('rollingBtn');
};

var dui = {};
dui.imgtab = function(o,p){
    o = $(o);
    var li = o.find("li");
    var a = o.find("a");
    var cur = li.find(".active");
    var idx = cur.size() == 0 ? '' : cur.text();
    o.data("ix", idx);
    a.on("mouseover",function(){
        var t = $(this);
        if(!t.parent().hasClass("active")){
            t.parent().addClass("active");
            setImg(t, true);
        }
    }).on("mouseout",function(){
        var t = $(this).parent(), ix = o.data("ix");
        if(ix != t.text()){
            $(this).parent().removeClass("active");
            setImg(t, false);
        }
    });
    li.find("a").on("click", function(){
        setActive($(this).parent());
    });
    function setImg(n,b){
        var h = n.parent().hasClass("active");
        var i = n.find("img");
        if(i.size()==0) return;
        var str = i.attr("src");
        var a = str.split('.');
        if(!b && !h){
            str = str.replace('.active', '');
            i.attr("src", str);
        }else if(b && h){
            str = str.replace(a[a.length - 1], 'active.'+a[a.length-1]);
            i.attr("src", str);
        }
    }
    function setActive(t){
        var li = o.find("li");
        li.removeClass("active");
        t.addClass("active");
        setImg(t.find("a"), true);
        o.data("ix", t.text());
    }
    o[0].setImg = setImg;
    o[0].setActive = setActive;
}


function getdatax(url, fn, opt){
    if(!window._store) window._store = {};
    var o = $.extend({
        cid: fn.name,
        cfn:fn,
        cdata:[],
        sid:fn.name+'_comb',
        schange:changeSelect,
        sidx:-1,
        sdata:[],
        svalue:'',
    }, opt);
    getdata(url, function(data){
        initStore(data, o); //init store
        initSelect(o.sdata, o.sid, o.svalue); //draw select
        fn(o.cdata, o.svalue);    //draw chart
    });
}
var regNum = /^(\-)?\d+(\.\d+)?/
function initStore(data, o){
    if(o.sidx>0){
        o.cdata = initStoreData(data, o.sidx);
    }else{
        o.cdata = data;
    }
    o.sdata = o.cdata.index;
    o.svalue = regNum.test(o.cdata.index[0]) ? o.cdata.index[o.cdata.index.length - 1] : o.cdata.index[0];
    _store[o.cid] = o; //save
}
function initStoreData(data, idx){
    var ds = {legend:[],xData:[],data:{}};
    ds.legend = data[0];
    ds.xData = data[1];
    var res = new Map();
    ds.index = data[idx].filter(function(a){
        return !res.has(a) && res.set(a, 1);
    });

    ds.index.forEach(function(x){
        var arr = [];
        for(var i = 3; i <data.length; i++){
            arr.push([]);
        }
        ds.data[x] = arr;
    });
    var j = -1;
    for(var i = 0; i <data.length; i++){
        if(i == 0 || i == 1 || i == idx)
            continue;
        j++;
        data[i].forEach(function(o, m){
            var key = data[idx][m];
            if(ds.data[key])
                ds.data[key][j].push(data[i][m]);
        });
    }
    return ds;
}
function changeSelect(sel, id){
    if(!id) id = sel.id.split('_')[0];
    var val = sel.value, o = _store[id], fn = o.cfn;
    fn(o.cdata, val);
}
function initSelect(data,id,shouValue){
    var str = '<select id="'+id+'_select" class="selectpicker form-control"  style="background-color: rgba(23,32,45,0);width: 70px;height: 20px;text-align: center;border-radius:10px;border: 1px solid #37bff4;" onchange="changeSelect(this)">';
    for (var i = 0; i < data.length; i++) {
        var ss = '';
        if (data[i] == shouValue) ss = '" selected = "selected"';
        str += '<option value = "'+data[i]+'" '+ss+'>'+data[i]+'</option>';
    }
    str = str+'</select>';
    var tbody=window.document.getElementById(id);
    if(tbody) tbody.innerHTML = str;
    $("#"+id).find(".selectpicker").selectpicker();
}
var __top = [{"name":"能源指标监控",url:"/module/produce/index",ico:'ico_mn_motion'},{"name":"安全态势感知",url:"/module/sys/portal",ico:'ico_mn_safe'},{"name":"实时运营监测",url:"/module/sys/motion1",ico:'ico_mn_bi'},{"name":"能源市场分析",url:"/module/statecn/index",ico:'ico_mn_power'},{"name":"国际能源态势",url:"/module/pattern/index",ico:'ico_mn_globe'}];
function initMenu(){
    var dt = __top;
    var str = [];
    for(var i = 0; i <dt.length; i++){
        str.push('<li>','<span class="bbg"></span><a href="', __host, dt[i].url,'"><img src="',__host,'/static/theme/znrh/', dt[i].ico, '.png" /><span>', dt[i].name , '</span></a>','</li>');
    }
    $("#appmenu").html(str.join(''));
}
function showMenu(){
    var w = '170px';
    $('.qmenu').toggle();
    if($('.qmenu').is(":hidden")){
        $(".qmenu-ico").css("margin-left",0);
    }else{
        $(".qmenu-ico").css("margin-left",w);
    }
}
