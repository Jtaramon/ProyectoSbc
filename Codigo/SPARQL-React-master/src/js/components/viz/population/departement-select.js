import React, { Component } from 'react';
import Select from 'js/components/shared/select';
import Spinner from 'js/components/shared/spinner';
import VizCommunes from './container-communes';
import countriesConnector from 'js/components/connectors/country';
import D from 'js/i18n';

class CountrySelect extends Component {
	constructor() {
		super();
		this.state = { country: '' };
		this.handleChangeType = country => this.setState({ country });
	}

	render() {
		const { countries } = this.props;
		const { country } = this.state;
		return (
			<React.Fragment>
				<div className="mui-row loading-row">
					<div className="mui-col-md-5 mui-col-md-offset-1">
						<Select
							label={D.municipalityIntoDepartment}
							options={countries}
							value={country}
							onChange={this.handleChangeType}
						/>
					</div>
				</div>
				{country && <VizCommunes country={country} />}
			</React.Fragment>
		);
	}
}

export default countriesConnector(CountrySelect, {
	loading: () => <Spinner text={D.loading} />,
});
