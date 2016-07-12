'use strict';

describe('Controller Tests', function() {

    describe('ObbligoInverdimento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockObbligoInverdimento, MockSuperficieInverdimento;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockObbligoInverdimento = jasmine.createSpy('MockObbligoInverdimento');
            MockSuperficieInverdimento = jasmine.createSpy('MockSuperficieInverdimento');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'ObbligoInverdimento': MockObbligoInverdimento,
                'SuperficieInverdimento': MockSuperficieInverdimento
            };
            createController = function() {
                $injector.get('$controller')("ObbligoInverdimentoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'istruttoriaApp:obbligoInverdimentoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
