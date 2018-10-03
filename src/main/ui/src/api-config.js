const hostname = window && window.location && window.location.hostname;
export const SCHEMAS_ROOT = hostname.startsWith('localhost') ? 'http://'+hostname +':8080' : 'https://'+hostname+'/api/schemas';
export const VERSIONS_ROOT = hostname.startsWith('localhost') ? 'http://'+hostname +':8080' : 'https://'+hostname+'/api/versions';
export const ISSUERS_ROOT = hostname.startsWith('localhost') ? 'http://'+hostname +':8080' : 'https://'+hostname+'/api/issuers';