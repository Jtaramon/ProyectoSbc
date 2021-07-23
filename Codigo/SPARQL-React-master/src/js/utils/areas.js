import D from 'js/i18n';

export const areas = [
	{ id: 'COMMUNE', label: D.municipality },
	{
		id: 'DEPARTEMENT',
		label: D.department,
	},
];

export const getTypeFromId = id => areas.find(a => a.id === id).type;
