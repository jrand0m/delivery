Ext.define('AV.view.city.Edit', {
    extend: 'Ext.window.Window',
    alias : 'widget.cityedit',
    height: 180,
    width: 415,
    layout: {
        type: 'fit'
    },
    title: 'City ',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'form',
                    bodyPadding: 10,
                    items: [
                        {
                            xtype: 'textfield',
                            name: 'cityNameEN',
                            fieldLabel: 'City name EN',
                            anchor: '100%'
                        },
                        {
                            xtype: 'textfield',
                            name: 'cityNameUA',
                            fieldLabel: 'City name UA',
                            anchor: '100%'
                        },
                        {
                            xtype: 'textfield',
                            name: 'cityNameRU',
                            fieldLabel: 'City name RU',
                            anchor: '100%'
                        },
                        {
                            xtype: 'checkboxfield',
			    inputValue:'true',
                            name: 'display',
                            fieldLabel: 'Display',
                            anchor: '33%'
                        }
                        
                    ]
                }
            ],
	    buttons:[	{

                            text: 'Save',
			    action: 'saveCityAct'
                        },
                        {
 
                            text: 'Cancel',
			    action: 'cancelCityAct',
			    scope: me,
			    handler:me.close
                        },
                        {
                      
                            text: 'Edit Streets',
			    action: 'editStreetsAct'
                        }]
        });

        me.callParent(arguments);
    }
});