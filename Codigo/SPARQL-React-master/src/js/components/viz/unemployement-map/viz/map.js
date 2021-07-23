import React from 'react';
import ColoredMap from 'js/components/shared/map/colored-map';
import { wktToGeojson } from 'js/utils/map/wkt-to-geojson';
import D from 'js/i18n';

export default ({ data, legend }) => {
	const colors = [
		'#a9f2a4',
		'#ef9191',
		'#7eeb80',
		'#e76c6c',
		'#74e35b',
		'#df4a4a',
		'#39db41',
		'#d72929',
		'#19d332',
		'#d00b0b',
	];
	const geoJsonData = data.map(({ contours, ...d }) => ({
		contours: wktToGeojson(contours),
		...d,
	}));

	const contentArray = [
		[D.country, 'label', ''],
		[D.unemployementTitle, 'value', '%'],
	];

	return (
		<ColoredMap
			data={geoJsonData}
			legend={legend}
			colors={colors}
			contentArray={contentArray}
		/>
	);
};
