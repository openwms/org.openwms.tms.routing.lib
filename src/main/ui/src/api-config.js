const hostname = window && window.location && window.location.hostname;
export const API_ROOT = hostname.startsWith('localhost') ? 'http://'+hostname+':8086/tms' : 'https://'+hostname+'/tms';
