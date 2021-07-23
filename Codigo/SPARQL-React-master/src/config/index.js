const MAIN_ENDPOINT = 'http://graphdb.linked-open-statistics.org/repositories';

export default {
	TEST_ENDPOINT: `${MAIN_ENDPOINT}/test`,
	COVID_ENDPOINT: `${MAIN_ENDPOINT}/CovidLatam`,
};

export const TOKEN = process.env.REACT_APP_MGL_TOKEN;
