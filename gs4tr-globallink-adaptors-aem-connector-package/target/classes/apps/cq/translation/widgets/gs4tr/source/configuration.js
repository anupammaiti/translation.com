
CQ.Ext.namespace("CQ.Translation");
CQ.Ext.namespace("CQ.Translation.GlobalLink");
CQ.Ext.namespace("CQ.Translation.GlobalLink.Connection");

CQ.Translation.GlobalLink.Connection = {

    webServicesURL: null,

    userName: null,

    userPassword: null,

    userProjects: null,

    projectShortCode: null,

    projectDefaultClassifier: null,

    getField: function(dialog, key) {
        var items = dialog.find("name", key);
        if ((CQ.Ext.isArray(items)) && (items.length > 0)) {
        	return items[0];
        }
    },

    getFiledValue: function(dialog, key) {
		var items = dialog.find("name", key);
        if ((CQ.Ext.isArray(items)) && (items.length > 0)) {
        	return items[0].getValue();
        }
    },

    isFieldEmpty: function(field, message) {

    	if (!field || field.getValue() == "") {

            CQ.Ext.Msg.show({
                title: "Error",
                msg: message,
                buttons: CQ.Ext.Msg.OK,
                icon: CQ.Ext.Msg.ERROR
            });

            return true;
        }

        return false;
    },

     saveCostInfo: function(dialog) {

         var langName = this.getField(dialog, './langName');

         var langCode = this.getField(dialog, './langCode');
         var newWords = this.getField(dialog, './newWords');
         var fuzzyWords = this.getField(dialog, './fuzzyWords');
         var hundMatches = this.getField(dialog, './100Matches');
         var repsWords = this.getField(dialog, './repsWords');
         var pmCost = this.getField(dialog, './pmCost');
         var pmCostSelection = this.getField(dialog, './pmCostSelection');
         
		//alert('pmCostSelection' + pmCostSelection.getValue());

         var path = '/etc/cloudservices/gs4tr-translation/language-cost';

         var node = CQ.Util.eval(path);

         if (node == null) {

             CQ.HTTP.post(path, function(options, success, response){if (!success){alert('Could not save data1');}}, {
  				"jcr:primaryType" : "cq:Page"
				});

		 }

         path = path + '/jcr:content';
         node = CQ.Util.eval(path);

         if (node == null) {

             CQ.HTTP.post(path, function(options, success, response){if (!success){alert('Could not save data6');}}, {
  				"jcr:primaryType" : "cq:PageContent"
				});
		 }

         path = '/etc/cloudservices/gs4tr-translation/language-cost/'+ langCode.getValue();
         node = CQ.Util.eval(path);

         if (node == null) {

             CQ.HTTP.post(path, function(options, success, response){if (!success){alert('Could not save data3');}}, {
  				"jcr:primaryType" : "cq:Page"
				});
		 }

		 path = path + '/jcr:content';
         node = CQ.Util.eval(path);

         /*
         if (node == null) {

             CQ.HTTP.post(path, function(options, success, response){if (!success){alert('Could not save data4');}}, {
  				"jcr:primaryType" : "cq:PageContent"
				});
		 } */

         CQ.HTTP.post(path, function(options, success, response){if (!success){alert('Could not save data5');}}, { 'jcr:primaryType' : 'cq:PageContent', langName : langName.getValue(),  
             										  langCode : langCode.getValue(), 
                                                      newWords : newWords.getValue(), 
                                                      fuzzyWords : fuzzyWords.getValue(), 
                                                      hundMatches : hundMatches.getValue(), 
                                                      repsWords : repsWords.getValue(),
                                                      pmCostSelection : pmCostSelection.getValue(),
                                                      pmCost : pmCost.getValue()});

        CQ.Ext.Msg.show({
                            title: "Info",
                            msg: "Successfully saved language cost.",
                            buttons: CQ.Ext.Msg.OK,
                            icon: CQ.Ext.Msg.INFO

                        });

     },

    pullUserProjects: function(dialog) {

        var webServicesURL = this.getField(dialog, './pdWebServicesURL');
        if (this.isFieldEmpty(webServicesURL, "Please enter web services URL.")) {
            return;
        }
        this.webServicesURL = webServicesURL.getValue();

		var userName = this.getField(dialog, './pdUserName');
        if (this.isFieldEmpty(userName, "Please enter user name.")) {
            return;
        }
        this.userName = userName.getValue();

		var userPassword = this.getField(dialog, './pdUserPassword');
        if (this.isFieldEmpty(userPassword, "Please enter user password.")) {
            return;
        }
        this.userPassword = userPassword.getValue();


        var projectShortCode = this.getField(dialog, './pdProjectShortCode');
        if (this.isFieldEmpty(projectShortCode, "Please enter projet short code.")) {
			return;
        }
        this.projectShortCode = projectShortCode.getValue();

        var projectDefaultClassifier = this.getField(dialog, './pdProjectDefaultClassifier');
        if (projectDefaultClassifier) {
			this.projectDefaultClassifier = projectDefaultClassifier.getValue();
        }

        CQ.HTTP.post("/bin/globallink-connector", function(options, success, response) {

                if(success) {

                    response = CQ.HTTP.eval(response);

                    if(response) {

						this.userProjects = CQ.Ext.util.JSON.encode(response);

                        CQ.Ext.Msg.show({
                            title: "Info",
                            msg: "Connection with GlobalLink established successfully.",
                            buttons: CQ.Ext.Msg.OK,
                            icon: CQ.Ext.Msg.INFO,
                            fn: function(){
								CQ.Ext.getCmp('gs4tr-configuration-save-btn').enable();
                            }
                        });

                    } else {

                        CQ.Ext.Msg.show({
                            title: "Error",
                            msg: "Connection with GlobalLink could not be established.<br/>Please check your configuration.",
                            buttons: CQ.Ext.Msg.OK,
                            icon: CQ.Ext.Msg.ERROR});
                    }

                } else {

                    CQ.Ext.Msg.alert("Error", "Error while connecting.");
                }

            }, {
				taskName: 'pullUserProjects',
                pdWebServicesURL: this.webServicesURL,
                pdUserName: this.userName,
                pdUserPassword: this.userPassword
            }, this, true);

    },

    saveConnectionConfig: function(dialog) {

		console.log('*** Call saveConnectionConfig() ***');

        if (this.userProjects == null) {

			CQ.Ext.Msg.show({
                title: "Error",
                msg: "Connection with GlobalLink was not established.<br/>Please connect with GlobalLink before you perform save action.",
                buttons: CQ.Ext.Msg.OK,
                icon: CQ.Ext.Msg.ERROR
            });

            return;
        }

        CQ.HTTP.post("/bin/globallink-connector", function(options, success, response) {

                if(success) {

					dialog.close();
					
					window.location.reload();

                } else {

                    CQ.Ext.Msg.alert("Error", "Unable to save connection configuration.");
                }

            }, {
				taskName: 'saveConnectionConfig',
                configPath: dialog.path,
                pdWebServicesURL: this.webServicesURL,
                pdUserName: this.userName,
                pdUserPassword: this.userPassword,
                pdProjectShortCode: this.projectShortCode,
                pdProjectDefaultClassifier: this.projectDefaultClassifier
            }, this, true);
	}

};
