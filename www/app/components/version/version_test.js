'use strict';

describe('hackathon.version module', function() {
  beforeEach(module('hackathon.version'));

  describe('version service', function() {
    it('should return current version', inject(function(version) {
      expect(version).toEqual('0.1');
    }));
  });
});
