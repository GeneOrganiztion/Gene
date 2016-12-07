//Author      : @arboshiki
//create lobibox object
var Lobibox = Lobibox || {};
(function(){
    
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------

    //User can set default properties for prompt in the following way
    //Lobibox.prompt.DEFAULT_OPTIONS = object;
    Lobibox.prompt = function (type, options) {
        return new LobiboxPrompt(type, options);
    };
    //User can set default properties for confirm in the following way
    //Lobibox.confirm.DEFAULT_OPTIONS = object;
    Lobibox.confirm = function (options) {
        return new LobiboxConfirm(options);
    };
    //User can set default properties for progress in the following way
    //Lobibox.progress.DEFAULT_OPTIONS = object;
    Lobibox.progress = function (options) {
        return new LobiboxProgress(options);
    };
    //Create empty objects in order user to be able to set default options in the following way
    //Lobibox.error.DEFAULT_OPTIONS = object;
    //Lobibox.success.DEFAULT_OPTIONS = object;
    //Lobibox.warning.DEFAULT_OPTIONS = object;
    //Lobibox.info.DEFAULT_OPTIONS = object;

    Lobibox.error = {};
    Lobibox.success = {};
    Lobibox.warning = {};
    Lobibox.info = {};

    //User can set default properties for alert in the following way
    //Lobibox.alert.DEFAULT_OPTIONS = object;
    Lobibox.alert = function (type, options) {
        if (["success", "error", "warning", "info"].indexOf(type) > -1) {
            return new LobiboxAlert(type, options);
        }
    };
    //User can set default properties for window in the following way
    //Lobibox.window.DEFAULT_OPTIONS = object;
    Lobibox.window = function (options) {
        return new LobiboxWindow('window', options);
    };

    
    /**
     * Base prototype for all messageboxes and window
     */
    var LobiboxBase = {
        $type       : null,
        $el         : null,
        $options    : null,
        debug       : function(){
            if (this.$options.debug){
                window.console.debug.apply(window.console, arguments);
            }
        },
        _processInput: function(options){
            var me = this;
            if ($.isArray(options.buttons)){
                var btns = {};
                for (var i=0; i<options.buttons.length; i++){
                    var btn = Lobibox.base.OPTIONS.buttons[options.buttons[i]];
                    
                    btns[options.buttons[i]] = btn;
                }
                options.buttons = btns;
            }
            options.customBtnClass = options.customBtnClass ? options.customBtnClass : Lobibox.base.DEFAULTS.customBtnClass;
            for (var i in options.buttons){
                var btn = options.buttons[i];
                if (options.buttons.hasOwnProperty(i)){
                    btn = $.extend({}, Lobibox.base.OPTIONS.buttons[i], btn);
                    if ( ! btn['class']){
                        btn['class'] = options.customBtnClass;
                    }
                }
                options.buttons[i] = btn;
            }
            options = $.extend({}, Lobibox.base.DEFAULTS, options);
            if (options.showClass === undefined) {
                options.showClass = Lobibox.base.OPTIONS.showClass;
            }
            if (options.hideClass === undefined) {
                options.hideClass = Lobibox.base.OPTIONS.hideClass;
            }
            if (options.baseClass === undefined) {
                options.baseClass = Lobibox.base.OPTIONS.baseClass;
            }
            if (options.delayToRemove === undefined) {
                options.delayToRemove = Lobibox.base.OPTIONS.delayToRemove;
            }
            return options;
        },
        _init: function(){
            var me = this;
            
            me._createMarkup();
            me.setTitle(me.$options.title);
            if (me.$options.draggable && ! me._isMobileScreen()){
                me.$el.addClass('draggable');
                me._enableDrag();
            }
            if (me.$options.closeButton){
                me._addCloseButton();
            }
            if (me.$options.closeOnEsc){
                $(document).on('keyup.lobibox', function(ev){
                    if (ev.which === 27){
                        me.destroy();
                    }
                });
            }
            if (me.$options.baseClass){
                me.$el.addClass(me.$options.baseClass);
            }
            if (me.$options.showClass){
                me.$el.removeClass(me.$options.hideClass);
                me.$el.addClass(me.$options.showClass);
            }
            me.$el.data('lobibox', me);
        },
        /**
         * 
         * 
         * @param {String} position "'top', 'center', 'bottom'"
         * @returns {Object}
         */
        _calculatePosition: function(position){
            var me = this;
            var top;
            if (position === 'top'){
                top = 30;
            }else if (position === 'bottom'){
                top = $(window).outerHeight() - me.$el.outerHeight() - 30;
            }else{
                top = ($(window).outerHeight() - me.$el.outerHeight())/2;
            }
            var left = ($(window).outerWidth() - me.$el.outerWidth())/2;
            return {
                left: left,
                top: top
            };
        },
        _createButton: function(type, op){
            var me = this;
            var btn = $('<button></button>')
                    .addClass(op['class'])
                    .attr('data-type', type)
                    .html(op.text);
            if (me.$options.callback && typeof me.$options.callback === 'function') {
                btn.on('click.lobibox', function(ev){
                    var bt = $(this);
                    if (me.$options.buttons[type] && me.$options.buttons[type].closeOnClick){
                        me.destroy();
                    }
                    me.$options.callback(me, bt.data('type'), ev);
                });
            }
            btn.click(function() {
                if (me.$options.buttons[type] && me.$options.buttons[type].closeOnClick){
                    me.destroy();
                }
            });
            return btn;
        },
        _generateButtons: function(){
            var me = this;
            var btns = [];
            for (var i in me.$options.buttons){
                if (me.$options.buttons.hasOwnProperty(i)){
                    var op = me.$options.buttons[i];
                    var btn = me._createButton(i, op);
                    btns.push(btn);
                }
            }
            return btns;
        },
        _createMarkup: function(){
            var me = this;
            var lobibox = $('<div class="lobibox"></div>');
            lobibox.attr('data-is-modal', me.$options.modal);
            var header = $('<div class="lobibox-header"></div>')
                    .append('<span class="lobibox-title orange"></span>')
                    ;
            var body = $('<div class="lobibox-body"></div>');
            lobibox.append(header);
            lobibox.append(body);
            if (me.$options.buttons && ! $.isEmptyObject(me.$options.buttons)){
                var footer = $('<div class="lobibox-footer"></div>');
                footer.append(me._generateButtons());
                lobibox.append(footer);
                if (Lobibox.base.OPTIONS.buttonsAlign.indexOf(me.$options.buttonsAlign) > -1){
                    footer.addClass('text-'+me.$options.buttonsAlign);
                }
            }
            me.$el = lobibox
                    .addClass(Lobibox.base.OPTIONS.modalClasses[me.$type])
                    ;
        },
        _setSize: function(){
            var me = this;
            me.setWidth(me.$options.width);
            if (me.$options.height === 'auto'){
                me.setHeight(me.$el.outerHeight());
            }else{
                me.setHeight(me.$options.height);
            }
        },
        _calculateBodyHeight: function(height){
            var me = this;
            var headerHeight = me.$el.find('.lobibox-header').outerHeight();
            var footerHeight = me.$el.find('.lobibox-footer').outerHeight();
            return height - (headerHeight ? headerHeight : 0) - (footerHeight ? footerHeight : 0);
            
        },
        /**
         * Add backdrop in case if backdrop does not exist
         * 
         * @returns {void}
         */
        _addBackdrop: function(){
            if ($('.lobibox-backdrop').length === 0){
                $('body').append('<div class="lobibox-backdrop"></div>');
            }
        },
        _triggerEvent: function(type){
            var me = this;
            if (me.$options[type] && typeof me.$options[type] === 'function'){
                me.$options[type](me);
            }
        },
        _calculateWidth: function(width){
            var me = this;
            width = Math.min($(window).outerWidth(), width);
            if (width === $(window).outerWidth()){
                width -= 2 * me.$options.horizontalOffset;
            }
            return width;
        },
        _calculateHeight: function(height){
            return Math.min($(window).outerHeight(), height);
        },
        _addCloseButton: function () {
            var me = this;
            var closeBtn = $('<span class="close">&times;</span>');
            me.$el.find('.lobibox-header').append(closeBtn);
            closeBtn.on('mousedown', function(ev){
                ev.stopPropagation();
            });
            closeBtn.on('click.lobibox', function (ev) {
                me.destroy();
            });
        },
        _position: function () {
            var me = this;

            me._setSize();
            var pos = me._calculatePosition();
            me.setPosition(pos.left, pos.top);
        },
        _isMobileScreen: function () {
            if ($(window).outerWidth() < 768) {
                return true;
            }
            return false;
        },
        _enableDrag: function () {
            var el = this.$el;
            var heading = el.find('.lobibox-header');
            heading.on('mousedown.lobibox', function (ev) {
                el.attr('offset-left', ev.offsetX);
                el.attr('offset-top', ev.offsetY);
                el.attr('allow-drag', 'true');
            });
            $(document).on('mouseup.lobibox', function (ev) {
                el.attr('allow-drag', 'false');
            });
            $(document).on('mousemove.lobibox', function (ev) {
                if (el.attr('allow-drag') === 'true') {
                    var left = ev.clientX - parseInt(el.attr('offset-left'), 10) - parseInt(el.css('border-left-width'), 10);
                    var top = ev.clientY - parseInt(el.attr('offset-top'), 10) - parseInt(el.css('border-top-width'), 10);
                    el.css({
                        left: left,
                        top: top
                    });
//                    el.css({
//                        right: $(document).outerWidth() - (left + el.outerWidth() + 2),
//                        bottom: $(document).outerHeight() - (top + el.outerHeight() + 2)
//                    });
                }
            });
//            el.draggable({
//                handle: '.lobibox-header'
//            });
//            el.css('position', '');
        },
        /**
         * Set the message of messagebox
         * 
         * @param {String} msg "new message of messagebox"
         * @returns {Instance}
         */
        _setContent: function (msg) {
            var me = this;
            me.$el.find('.lobibox-body').html(msg);
            return me;
        },
//------------------------------------------------------------------------------
//--------------------------PUBLIC METHODS--------------------------------------
//------------------------------------------------------------------------------
        /**
         * Hide the messagebox
         * 
         * @returns {Instance}
         */
        hide: function () {
            var me = this;
            if (me.$options.hideClass) {
                me.$el.removeClass(me.$options.showClass);
                me.$el.addClass(me.$options.hideClass);
                setTimeout(function () {
                     callback();
                }, me.$options.delayToRemove);
            } else {
                callback();
            }
            function callback(){
                me.$el.addClass('lobibox-hidden');
                if ($('.lobibox[data-is-modal=true]:not(.lobibox-hidden)').length === 0) {
                    $('.lobibox-backdrop').remove();
                    $('body').removeClass(Lobibox.base.OPTIONS.bodyClass);
                }
            }
            return this;
        },
        /**
         * Removes the messagebox from document
         * 
         * @returns {Instance}
         */
        destroy: function () {
            var me = this;
            me._triggerEvent('beforeClose');
            if (me.$options.hideClass) {
                me.$el.removeClass(me.$options.showClass);
                me.$el.addClass(me.$options.hideClass);
                setTimeout(function(){
                    callback();
                },me.$options.delayToRemove);
            }else{
                callback();
            }
            function callback(){
                me.$el.remove();
                if ($('.lobibox[data-is-modal=true]').length === 0) {
                    $('.lobibox-backdrop').remove();
                    $('body').removeClass(Lobibox.base.OPTIONS.bodyClass);
                }
                me._triggerEvent('closed');
            }
            return this;
        },
        /**
         * Set the width of messagebox
         * 
         * @param {Integer} width "new width of messagebox"
         * @returns {Instance}
         */
        setWidth: function (width) {
            width = this._calculateWidth(width);
            this.$el.css('width', width);
            return this;
        },
        /**
         * Set the height of messagebox
         * 
         * @param {Integer} height "new height of messagebox"
         * @returns {Instance}
         */
        setHeight: function (height) {
            var me = this;
            height = me._calculateHeight(height);
            me.$el.css('height', height);
            var bHeight = me._calculateBodyHeight(me.$el.innerHeight());
            me.$el.find('.lobibox-body').css('height', bHeight);
            return me;
        },
        /**
         * Set the width and height of messagebox
         * 
         * @param {Integer} width "new width of messagebox"
         * @param {Integer} height "new height of messagebox"
         * @returns {Instance}
         */
        setSize: function (width, height) {
            var me = this;
            me.setWidth(width);
            me.setHeight(height);
            return me;
        },
        /**
         * Set position of messagebox
         * 
         * @param {Integer|String} left "left coordinate of messsagebox or string representaing position. Available: ('top', 'center', 'bottom')"
         * @param {Integer} top
         * @returns {Instance}
         */
        setPosition: function (left, top) {
            var me = this;
            var position;
            if (typeof left === 'number' && typeof top === 'number'){
                position = {
                    left: left,
                    top: top
                };
            }else if (typeof left === 'string'){
                position = me._calculatePosition(left);
            }
            me.$el.css(position);
            return me;
        },
        /**
         * Set the title of messagebox
         * 
         * @param {String} title "new title of messagebox"
         * @returns {Instance}
         */
        setTitle: function (title) {
            var me = this;
            me.$el.find('.lobibox-title').html(title);
            return me;
        },
        /**
         * Get the title of messagebox
         * 
         * @returns {String}
         */
        getTitle: function(){
            var me = this;
            return me.$el.find('.lobibox-title').html();
        },
        /**
         * Show messagebox
         * 
         * @returns {Instance}
         */
        show: function () {
            var me = this;
            me._triggerEvent('onShow');
            me.$el.removeClass('lobibox-hidden');
            $('body').append(me.$el);
            if (me.$options.modal) {
                $('body').addClass(Lobibox.base.OPTIONS.bodyClass);
                me._addBackdrop();
            }
            me._triggerEvent('shown');
            return me;
        }
    };
    //User can set default options by this variable
    Lobibox.base = {};
    Lobibox.base.OPTIONS = {
        bodyClass       : 'lobibox-open',
        
        modalClasses : {
            'error'     : 'lobibox-error',
            'success'   : 'lobibox-success',
            'info'      : 'lobibox-info',
            'warning'   : 'lobibox-warning',
            'confirm'   : 'lobibox-confirm',
            'progress'  : 'lobibox-progress',
            'prompt'    : 'lobibox-prompt',
            'default'   : 'lobibox-default',
            'window'    : 'lobibox-window'
        },
        buttonsAlign: ['left', 'center', 'right'],
        buttons: {
            ok: {
                'class': 'lobibox-btn lobibox-btn-default',
                text: 'OK',
                closeOnClick: true
            },
            cancel: {
                'class': 'lobibox-btn lobibox-btn-cancel',
                text: 'Cancel',
                closeOnClick: true
            },
            yes: {
                'class': 'lobibox-btn lobibox-btn-yes',
                text: '确定',
                closeOnClick: true
            },
            no: {
                'class': 'lobibox-btn lobibox-btn-no',
                text: '取消',
                closeOnClick: true
            }
        }
    };
    Lobibox.base.DEFAULTS = {
        horizontalOffset: 5,    //If the messagebox is larger (in width) than window's width. The messagebox's width is reduced to window width - 2 * horizontalOffset
        width           : 600,
        height          : 'auto',  // Height is automatically given calculated by width
        closeButton     : true,  // Show close button or not
        draggable       : false,  // Make messagebox draggable 
        customBtnClass  : 'lobibox-btn lobibox-btn-default', // Class for custom buttons
        modal           : true,
        debug           : false,
        buttonsAlign    : 'right', // Position where buttons should be aligned
        closeOnEsc      : true,  // Close messagebox on Esc press
        delayToRemove   : 200,
        baseClass       : 'animated-super-fast',
        showClass       : 'zoomIn',
        hideClass       : 'zoomOut',
        
        
        //events
        //When messagebox show is called but before it is actually shown
        onShow          : null,
        //After messagebox is shown
        shown           : null,
        //When messagebox remove method is called but before it is actually hidden
        beforeClose     : null,
        //After messagebox is hidden
        closed          : null
    };
//------------------------------------------------------------------------------
//-------------------------LobiboxPrompt----------------------------------------
//------------------------------------------------------------------------------
    function LobiboxPrompt (type, options){
        this.$input         = null;
        this.$type          = 'prompt';
        this.$promptType    = type;
        
        options = $.extend({}, Lobibox.prompt.DEFAULT_OPTIONS, options);
        
        this.$options = this._processInput(options);
        
        this._init();
        this.debug(this);
    };
    
    LobiboxPrompt.prototype = $.extend({}, LobiboxBase, {
        constructor: LobiboxPrompt,
        
        _processInput: function(options){
            var me = this;
            
            var mergedOptions = LobiboxBase._processInput.call(me, options);
            mergedOptions.buttons = {
                ok: Lobibox.base.OPTIONS.buttons.ok,
                cancel: Lobibox.base.OPTIONS.buttons.cancel
            };
            options = $.extend({}, mergedOptions, LobiboxPrompt.DEFAULT_OPTIONS, options);
            return options;
        },
        _init: function(){
            var me = this;
            
            LobiboxBase._init.call(me);
            
            me.show();
            me._setContent(me._createInput());
//            me.$input.focus();
            me._position();
            me.$input.focus();
        },
        _createInput: function(){
            var me = this;
            var label;
            if (me.$options.multiline){
                me.$input = $('<textarea></textarea>');
                me.$input.attr('rows' , me.$options.lines);
            }else{
                me.$input = $('<input type="'+me.$promptType+'"/>');
            }
            me.$input.addClass('lobibox-input');
            me.$input.attr(me.$options.attrs);
            if (me.$options.value){
                me.setValue(me.$options.value);
            }
            if (me.$options.label){
                label = $('<label>'+me.$options.label+'</label>');
            }
            var innerHTML = $('<div></div>').append(label, me.$input);
            return innerHTML;
        },
        /**
         * Set value of input
         * 
         * @param {Strin} val "value of input"
         * @returns {Instance}
         */
        setValue: function(val){
            this.$input.val(val);
            return this;
        },
        /**
         * Get value of input
         * 
         * @returns {String}
         */
        getValue: function(){
            return this.$input.val();
        }
    });
    
    LobiboxPrompt.DEFAULT_OPTIONS = {
        width: 400,
        attrs : {},         // Object of any valid attribute of input field
        value: '',          // Value which is given to textfield when messagebox is created
        multiline: false,   // Set this true for multiline prompt
        lines: 3,           // This works only for multiline prompt. Number of lines
        type: 'text',       // Prompt type. Available types (text|number|color)
        label: ''           // Set some text which will be shown exactly on top of textfield
    };
//------------------------------------------------------------------------------
//-------------------------LobiboxConfirm---------------------------------------
//------------------------------------------------------------------------------
    function LobiboxConfirm (options){
        this.$type      = 'confirm';
        
//        options = $.extend({}, Lobibox.confirm.DEFAULT_OPTIONS, options);
        
        this.$options   = this._processInput(options);
        this._init();
        this.debug(this);
    };
    
    LobiboxConfirm.prototype = $.extend({}, LobiboxBase, {
        constructor: LobiboxConfirm,
        
        _processInput: function(options){
            var me = this;
            
            var mergedOptions = LobiboxBase._processInput.call(me, options); 
            mergedOptions.buttons = {
                yes: Lobibox.base.OPTIONS.buttons.yes,
                no: Lobibox.base.OPTIONS.buttons.no
            };
            options = $.extend({}, mergedOptions, Lobibox.confirm.DEFAULTS, options);
            return options;
        },
        
        _init: function(){
            var me = this;
            
            LobiboxBase._init.call(me);
            me.show();
            var d = $('<div></div>');
            if (me.$options.iconClass){
                d.append($('<div class="lobibox-icon-wrapper"></div>')
                    .append('<i class="lobibox-icon '+me.$options.iconClass+'"></i>'))
                    ;
            }
            d.append('<div class="lobibox-body-text-wrapper"><span class="lobibox-body-text">'+me.$options.msg+'</span></div>');
            me._setContent(d.html());
            
            me._position();
        }
    });
    
    Lobibox.confirm.DEFAULTS = {
        title           : '信息确认',
        width           : 500,
        iconClass       : 'glyphicon glyphicon-question-sign'
    };
//------------------------------------------------------------------------------
//-------------------------LobiboxAlert------------------------------------------
//------------------------------------------------------------------------------
    function LobiboxAlert (type, options){
        this.$type      = type;

//        options = $.extend({}, Lobibox.alert.DEFAULT_OPTIONS, Lobibox[type].DEFAULT_OPTIONS, options);

        this.$options   = this._processInput(options);

        this._init();
        this.debug(this);
    };

    LobiboxAlert.prototype = $.extend({}, LobiboxBase, {
        constructor: LobiboxAlert,

        _processInput: function(options){
            
//            ALERT_OPTIONS = $.extend({}, LobiboxAlert.OPTIONS, Lobibox.alert.DEFAULTS);

            var me = this;
            var mergedOptions = LobiboxBase._processInput.call(me, options);
            mergedOptions.buttons = {
                ok: Lobibox.base.OPTIONS.buttons.ok
            };
            options = $.extend({}, mergedOptions, Lobibox.alert.OPTIONS[me.$type], Lobibox.alert.DEFAULTS, options);
//            window.console.log(options);
//            options = $.extend({}, mergedOptions, LobiboxAlert.DEFAULT_OPTIONS, options);
//            if (options.iconClass === true){
//                options.iconClass = ALERT_OPTIONS[me.$type].iconClass;
//            }
            
            return options;
        },

        _init: function(){
            var me = this;
            LobiboxBase._init.call(me);
            me.show();

            var d = $('<div></div>');
            if (me.$options.iconClass){
                d.append($('<div class="lobibox-icon-wrapper"></div>')
                    .append('<i class="lobibox-icon '+me.$options.iconClass+'"></i>'))
                    ;
            }
            d.append('<div class="lobibox-body-text-wrapper"><span class="lobibox-body-text">'+me.$options.msg+'</span></div>');
            me._setContent(d.html());
            me._position();
        }
    });
    Lobibox.alert.OPTIONS = {
        warning: {
            title: 'Warning',
            iconClass: 'glyphicon glyphicon-question-sign'
        },
        info: {
            title: 'Information',
            iconClass: 'glyphicon glyphicon-info-sign'
        },
        success: {
            title: 'Success',
            iconClass: 'glyphicon glyphicon-ok-sign'
        },
        error: {
            title: 'Error',
            iconClass: 'glyphicon glyphicon-remove-sign'
        }
    };
    //User can set default options by this variable
    Lobibox.alert.DEFAULTS = {
//        title: 
//        iconClass: 
    };
//------------------------------------------------------------------------------
//-------------------------LobiboxProgress--------------------------------------
//------------------------------------------------------------------------------
    function LobiboxProgress (options){
        this.$type      = 'progress';
        this.$progressBarElement = null,
        
        this.$options   = this._processInput(options);
        this.$progress  = 0;
        
        this._init();
        this.debug(this);
    };
    
    LobiboxProgress.prototype = $.extend({}, LobiboxBase, {
        constructor: LobiboxProgress,
        
        _processInput: function(options){
            var me = this;
            var mergedOptions = LobiboxBase._processInput.call(me, options); 
             
            options = $.extend({}, mergedOptions, Lobibox.progress.DEFAULTS, options);
            return options;
        },
        _init: function(){
            var me = this;
            
            LobiboxBase._init.call(me);
            me.show();
            if (me.$options.progressTpl){
                me.$progressBarElement = $(me.$options.progressTpl);
            }else{
                me.$progressBarElement = me._createProgressbar();
            }
            var label;
            if (me.$options.label){
                label = $('<label>'+me.$options.label+'</label>');
            }
            var innerHTML = $('<div></div>').append(label, me.$progressBarElement);
            me._setContent(innerHTML);
            me._position();
        },
        _createProgressbar: function(){
            var me = this;
            var outer = $('<div class="lobibox-progress-bar-wrapper lobibox-progress-outer"></div>')
                    .append('<div class="lobibox-progress-bar lobibox-progress-element"></div>')
                    ;
            if (me.$options.showProgressLabel){
                outer.append('<span class="lobibox-progress-text" data-role="progress-text"></span>');
            }
           
            return outer;
        },
        /**
         * Set progress value
         * 
         * @param {Integer} progress "progress value"
         * @returns {Instance}
         */
        setProgress: function(progress){
            var me = this;
            if (me.$progress === 100){
                return;
            }
            progress = Math.min(100, Math.max(0, progress));
            me.$progress = progress;
            me._triggerEvent('progressUpdated');
            if (me.$progress === 100){
                me._triggerEvent('progressCompleted');
            }
            me.$el.find('.lobibox-progress-element').css('width', progress.toFixed(1)+"%");
            me.$el.find('[data-role="progress-text"]').html(progress.toFixed(1)+"%");
            return me;
        },
        /**
         * Get progress value
         * 
         * @returns {Integer}
         */
        getProgress: function(){
            return this.$progress;
        }
    });
    
    Lobibox.progress.DEFAULTS = {
        width               : 500,
        showProgressLabel   : true,  // Show percentage of progress
        label               : '',  // Show progress label
        progressTpl         : false,  //Template of progress bar
        
        //Events
        progressUpdated     : null,
        progressCompleted   : null
    };
//------------------------------------------------------------------------------
//-------------------------LobiboxWindow----------------------------------------
//------------------------------------------------------------------------------
    function LobiboxWindow(type, options) {
        this.$type = type;
        
        this.$options = this._processInput(options);

        this._init();
        this.debug(this);
    }
    ;

    LobiboxWindow.prototype = $.extend({}, LobiboxBase, {
        constructor: LobiboxWindow,
        _processInput: function(options) {
            var me = this;
            var mergedOptions = LobiboxBase._processInput.call(me, options);
            
            if (options.content && typeof options.content === 'function'){
                options.content = options.content();
            }
            if (options.content instanceof jQuery){
                options.content = options.content.clone();
            }
            options = $.extend({}, mergedOptions, Lobibox.window.DEFAULTS, options);
            return options;
        },
        _init: function() {
            var me = this;

            LobiboxBase._init.call(me);
            me.setContent(me.$options.content);
            if (me.$options.url && me.$options.autoload){
                if ( ! me.$options.showAfterLoad){
                    me.show();
                    me._position();
                }
                me.load(function(){
                    if (me.$options.showAfterLoad) {
                        me.show();
                        me._position();
                    }
                });
            }else{
                me.show();
                me._position();
            }
        },
        /**
         * Setter method for <code>params</code> option
         * 
         * @param {Object} params "new params"
         * @returns {Instance}
         */
        setParams: function(params){
            var me = this;
            me.$options.params = params;
            return me;
        },
        /**
         * Getter method for <code>params</code>
         * 
         * @returns {Object}
         */
        getParams: function(){
            var me = this;
            return me.$options.params;
        },
        /**
         * Setter method of <code>loadMethod</code> option
         * 
         * @param {String} method "new method"
         * @returns {Instance}
         */
        setLoadMethod: function(method){
            var me = this;
            me.$options.loadMethod = method;
            return me;
        },
        /**
         * Getter method for <code>loadMethod</code> option
         * 
         * @returns {String}
         */
        getLoadMethod: function(){
            var me = this;
            return me.$options.loadMethod;
        },
        /**
         * Setter method of <code>content</code> option. 
         * Change the content of window
         * 
         * @param {String} content "new content"
         * @returns {Instance}
         */
        setContent: function(content){
            var me = this;
            me.$options.content = content;
            me.$el.find('.lobibox-body').html('').append(content);
            return me;
        },
        /**
         * Getter method of <code>content</code> option
         * 
         * @returns {String}
         */
        getContent: function(){
            var me = this;
            return me.$options.content;
        },
        /**
         * Setter method of <code>url</code> option
         * 
         * @param {String} url "new url"
         * @returns {Instance}
         */
        setUrl : function(url){
            this.$options.url = url;
            return this;
        },
        /**
         * Getter method of <code>url</code> option
         * 
         * @returns {String}
         */
        getUrl : function(){
            return this.$options.url;
        },
        /**
         * Loads content to window by ajax from specific url
         * 
         * @param {Function} callback "callback function"
         * @returns {Instance}
         */
        load: function(callback){
            var me = this;
            if ( ! me.$options.url){
                return me;
            }
            $.ajax(me.$options.url, {
                method: me.$options.loadMethod,
                data: me.$options.params
            }).done(function(res) {
                me.setContent(res);
                if (callback && typeof callback === 'function'){
                    callback(res);
                }
            });
            return me;
        }
    });

    Lobibox.window.DEFAULTS = {
        width           : 480,
        height          : 600,
        content         : '',  // HTML Content of window
        url             : '',  // URL which will be used to load content
        draggable       : true,  // Override default option
        autoload        : true,  // Auto load from given url when window is created
        loadMethod      : 'GET',  // Ajax method to load content
        showAfterLoad   : true,  // Show window after content is loaded or show and then load content
        params          : {}  // Parameters which will be send by ajax for loading content
    };
    
})();



//Author      : @arboshiki
/**
 * Generates random string of n length. 
 * String contains only letters and numbers
 * 
 * @param {int} n
 * @returns {String}
 */
Math.randomString = function(n) {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    for (var i = 0; i < n; i++)
        text += possible.charAt(Math.floor(Math.random() * possible.length));

    return text;
};
var Lobibox = Lobibox || {};
(function(){
        
    var LobiboxNotify = function(type, options) {
//------------------------------------------------------------------------------
//----------------PROTOTYPE VARIABLES-------------------------------------------
//------------------------------------------------------------------------------
        this.$type;
        this.$options;
        this.$el;
        this.$sound;
//------------------------------------------------------------------------------
//-----------------PRIVATE VARIABLES--------------------------------------------
//------------------------------------------------------------------------------        
        var me = this;
//------------------------------------------------------------------------------
//-----------------PRIVATE FUNCTIONS--------------------------------------------
//------------------------------------------------------------------------------
        var _processInput = function(options){
            
            if (options.size === 'mini' || options.size === 'large'){
                options.width = options.width || Lobibox.notify.OPTIONS[options.size].width;
            }
            options = $.extend({}, Lobibox.notify.OPTIONS[me.$type], Lobibox.notify.DEFAULTS, options);
            
            
            if (options.size !== 'mini' && options.title === true){
                options.title = Lobibox.notify.OPTIONS[me.$type].title;
            }else if (options.size === 'mini' && options.title === true){
                options.title = false;
            }
            if (options.icon === true){
                options.icon = Lobibox.notify.OPTIONS[me.$type].icon;
            }
            if (options.sound === true){
                options.sound = Lobibox.notify.OPTIONS[me.$type].sound;
            }
            if (options.sound){
                options.sound = options.soundPath + options.sound + options.soundExt;
            }
            
            return options;
        };
        var _init = function(){
            // Create notification
            var notify = _createNotify();
            var wrapper = _createNotifyWrapper();
            _appendInWrapper(notify, wrapper);
            
            me.$el = notify;
            if (me.$options.sound){
                var snd = new Audio(me.$options.sound); // buffers automatically when created
                snd.play();
            }
            me.$el.data('lobibox', me);
        };
        var _appendInWrapper = function($el, $wrapper){
            if (me.$options.size === 'normal'){
                $wrapper.append($el);
            }else if (me.$options.size === 'mini'){
                $el.addClass('notify-mini');
                $wrapper.append($el);
            }else if (me.$options.size === 'large'){
                var tabPane = _createTabPane();
                tabPane.append($el);
                var tabControl = _createTabControl(tabPane.attr('id'));
                $wrapper.find('.tab-content').append(tabPane);
                $wrapper.find('.nav-tabs').append(tabControl);
                tabControl.find('>a').tab('show');
            }
        };
        var _createTabControl = function(tabPaneId){
            var $li = $('<li></li>');
            $('<a href="#'+tabPaneId+'"></a>')
                    .attr('data-toggle', 'tab')
                    .attr('role', 'tab')
                    .append('<i class="tab-control-icon ' + me.$options.icon + '"></i>')
                    .appendTo($li);
            $li.addClass(Lobibox.notify.OPTIONS[me.$type]['class']);
            return $li;
        };
        var _createTabPane = function(){
            var $pane = $('<div></div>')
                    .addClass('tab-pane')
                    .attr('id', Math.randomString(10));
            return $pane;
        };
        var _createNotifyWrapper = function(){
            var selector;
            if (me.$options.size === 'large'){
                selector = '.lobibox-notify-wrapper-large';
            }else{
                selector = '.lobibox-notify-wrapper';
            }
            
            var classes = me.$options.position.split(" ");
            selector += "."+classes.join('.');
            var wrapper = $(selector);
            if (wrapper.length === 0){
                wrapper = $('<div></div>')
                        .addClass(selector.replace(/\./g, ' ').trim())
                        .appendTo($('body'));
                if (me.$options.size === 'large'){
                    wrapper.append($('<ul class="nav nav-tabs"></ul>'))
                            .append($('<div class="tab-content"></div>'));
                }
            }
            return wrapper;
        };
        var _createNotify = function(){
            var notify = $('<div class="lobibox-notify"></div>')
            // Add color class
                    .addClass(Lobibox.notify.OPTIONS[me.$type]['class'])
            // Add default animation class
                    .addClass(Lobibox.notify.OPTIONS['class'])
            // Add specific animation class
                    .addClass(me.$options.showClass);
            
            // Create icon wrapper class
            var iconWrapper = $('<div class="lobibox-notify-icon"></div>').appendTo(notify);

            // Add image or icon depending on given parameters
            if (me.$options.img) {
                var img = iconWrapper.append('<img src="' + me.$options.img + '"/>');
                iconWrapper.append(img);
            } else if (me.$options.icon) {
                var icon = iconWrapper.append('<i class="' + me.$options.icon + '"></i>');
                iconWrapper.append(icon);
            }else{
                notify.addClass('without-icon');
            }
            // Create body, append title and message in body and append body in notification
            var $body = $('<div></div>')
                    .addClass('lobibox-notify-body')
                    .append('<div class="lobibox-notify-msg">' + me.$options.msg + '</div>')
                    .appendTo(notify);
            if (me.$options.title){
                $body.prepend('<div class="lobibox-notify-title">' + me.$options.title + '<div>');
            }
            _addCloseButton(notify);
            if (me.$options.size === 'normal' || me.$options.size === 'mini'){
                _addCloseOnClick(notify);
                _addDelay(notify);
            }
            
            // Give width to notification
            if (me.$options.width){
                notify.css('width', _calculateWidth(me.$options.width));
            }
            
            return notify;
        };
        var _addCloseButton = function($el){
            if ( ! me.$options.closable){
                return;
            }
            var close = $('<span class="lobibox-close">&times;</span>');
            $el.append(close);
            close.click(function(ev){
                me.remove();
            });
        };
        var _addCloseOnClick = function($el){
            if ( ! me.$options.closeOnClick){
                return;
            }
            $el.click(function(){
                me.remove();
            });
        };
        var _addDelay = function($el){
            if ( ! me.$options.delay){
                return;
            }
            if (me.$options.delayIndicator){
                var delay = $('<div class="lobibox-delay-indicator"><div></div></div>');
                $el.append(delay);
            }
            var time = 0;
            var interval = 1000/30;
            var timer = setInterval(function(){
                time += interval;
                var width = 100 * time / me.$options.delay;
                if (width >= 100){
                    width = 100;
                    me.remove();
                    timer = clearInterval(timer);
                }
                if (me.$options.delayIndicator){
                    delay.find('div').css('width', width+"%");
                }
               
            }, interval);
        };
        var _findTabToActivate = function($li){
            var $itemToActivate = $li.prev();
            if ($itemToActivate.length === 0){
                $itemToActivate = $li.next();
            }
            if ($itemToActivate.length === 0){
                return null;
            }
            return $itemToActivate.find('>a');
        };
        var _calculateWidth = function(width){
            width = Math.min($(window).outerWidth(), width);
            return width;
        };
//------------------------------------------------------------------------------
//----------------PROTOTYPE FUNCTIONS-------------------------------------------
//------------------------------------------------------------------------------
        /**
         * Delete the notification
         * 
         * @returns {Instance}
         */
        this.remove = function(){
            me.$el.removeClass(me.$options.showClass)
                    .addClass(me.$options.hideClass);
            var parent = me.$el.parent();
            var wrapper = parent.closest('.lobibox-notify-wrapper-large');

            var href = '#' + parent.attr('id');

            var $li = wrapper.find('>.nav-tabs>li:has(a[href="' + href + '"])');
            $li.addClass(Lobibox.notify.OPTIONS['class'])
                    .addClass(me.$options.hideClass);
            setTimeout(function(){
                if (me.$options.size === 'normal' || me.$options.size === 'mini'){
                    me.$el.remove();
                }else if (me.$options.size === 'large'){
                    
                    var $itemToActivate = _findTabToActivate($li);
                    if ($itemToActivate){
                        $itemToActivate.tab('show');
                    }
                    $li.remove();
                    parent.remove();
                }
            }, 500);
            return me;
        };
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
//------------------------------------------------------------------------------
        this.$type = type;
        this.$options = _processInput(options);
//        window.console.log(me);
        _init();
    };
    
    Lobibox.notify = function(type, options){
        if (["info", "warning", "error", "success"].indexOf(type) > -1){
            return new LobiboxNotify(type, options);
        }
    };
    //User can set default options to this variable
    Lobibox.notify.DEFAULTS = {
        title: true,                // Title of notification. If you do not include the title in options it will automatically takes its value 
        //from Lobibox.notify.OPTIONS object depending of the type of the notifications or set custom string. Set this false to disable title
        size: 'normal',             // normal, mini, large
        soundPath: 'src/sounds/',   // The folder path where sounds are located
        soundExt: '.ogg',           // Default extension for all sounds
        showClass: 'zoomIn',        // Show animation class.
        hideClass: 'zoomOut',       // Hide animation class.
        icon: true,                 // Icon of notification. Leave as is for default icon or set custom string
        msg: '',                    // Message of notification
        img: null,                  // Image source string
        closable: false,             // Make notifications closable
        delay: 5000,                // Hide notification after this time (in miliseconds)
        delayIndicator: true,       // Show timer indicator
        closeOnClick: true,         // Close notifications by clicking on them
        width: 400,                 // Width of notification box
        sound: true,                // Sound of notification. Set this false to disable sound. Leave as is for default sound or set custom soud path
        position: "top center"    // Place to show notification. Available options: "top left", "top right", "bottom left", "bottom right"
    };
    //This variable is necessary.
    Lobibox.notify.OPTIONS = {
        //'class': 'animated-fast',
        large: {
            width: 500
        },
        mini: {
            'class': 'notify-mini'
        },
        success: {
            'class': 'lobibox-notify-success',
            'title': 'Success',
            'icon': 'glyphicon glyphicon-ok-sign',
            sound: 'sound2'
        },
        error: {
            'class': 'lobibox-notify-error',
            'title': 'Error',
            'icon': 'glyphicon glyphicon-remove-sign',
            sound: 'sound4'
        },
        warning: {
            'class': 'lobibox-notify-warning',
            'title': 'Warning',
            'icon': 'glyphicon glyphicon-exclamation-sign',
            sound: 'sound5'
        },
        info: {
            'class': 'lobibox-notify-info',
            'title': 'Information',
            'icon': 'glyphicon glyphicon-info-sign',
            sound: 'sound6'
        }
    };
})();


