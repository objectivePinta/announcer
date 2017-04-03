export const ROOT = window.location;
console.log(window.location.protocol);
console.log(window.location.host);
console.log(window.location.pathname.split('/')[1]);
export const API_ROOT = ROOT.protocol + "//" + ROOT.host + "/api/";

