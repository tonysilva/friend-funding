'use strict';

angular.module('hackathon.version', [
  'hackathon.version.interpolate-filter',
  'hackathon.version.version-directive'
])

.value('version', '0.1');
