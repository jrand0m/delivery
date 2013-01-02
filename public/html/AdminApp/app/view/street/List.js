Ext.define('AV.view.street.List', {
    extend: 'Ext.window.Window',

    height: 471,
    width: 706,
    layout: {
        type: 'fit'
    },
    title: 'Streets for ',

    initComponent: function() {
        var me = this;

        Ext.applyIf(me, {
            items: [
                {
                    xtype: 'gridpanel',
                    store: 'Street',
                    viewConfig: {

                    },
                    dockedItems: [
                        {
                            xtype: 'toolbar',
                            dock: 'top',
                            items: [
                                {
                                    xtype: 'combobox',
                                    fieldLabel: 'City',
                                    displayField: 'cityNameEN',
                                    store: 'City'
                                },
                                {
                                    xtype: 'button',
                                    text: 'Add new street'
                                }
                            ]
                        }
                    ],
                    columns: [
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'title_en',
                            text: 'Title_en'
                        },
                        {
                            xtype: 'gridcolumn',
                            dataIndex: 'title_ua',
                            text: 'Title_ua'
                        },
                        {
                            xtype: 'booleancolumn',
                            dataIndex: 'use',
                            text: 'Use'
                        }
                    ]
                }
            ]
        });
	
        me.callParent(arguments);
    }
});