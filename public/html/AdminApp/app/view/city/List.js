Ext.define('AV.view.city.List', {
    extend: 'Ext.panel.Panel',
    alias: 'widget.citylist',
    title: 'Cities',

    initComponent: function() {
      console.log("AV.view.city.List start");  
      var me = this;
        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'gridpanel',
		   
                    store: 'City',
                    viewConfig: {

                    },
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            id: 'cityToolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'button',
                                    id: 'addCityBtn',
				    action: 'addCityAction',
                                    text: 'add city'
                                }
                            ]
                        }
                    ],
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'cityNameUA',
                            text: 'CityNameUA'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'cityNameEN',
                            text: 'CityNameEN'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'cityNameRU',
                            text: 'CityNameRU'
                        },
                        {
                            xtype: 'booleancolumn',
                            dataIndex: 'display',
                            text: 'Display'
                        }
                    ]
                }
            ]
        });

      
      console.log("AV.view.city.List end");
      this.callParent(arguments);
    }
});