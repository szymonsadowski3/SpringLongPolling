import grequests
import json

def resolve_error(request, exception):
    assert(False)

def expect_timeout(request, exception):
    print('Timeout occured as expected!')

def test_timeout():
    getUrl = 'http://localhost:8080/api/newNotification'
    reqs = [grequests.get(getUrl, timeout=10)]
    results = grequests.map(reqs, exception_handler=expect_timeout)

    for result in results:
        assert(result is None)

def test_success_resolve():
    getUrl = 'http://localhost:8080/api/newNotification'
    postUrl = 'http://localhost:8080/api/notification'

    reqs = [
        grequests.get(getUrl, timeout=10),
        grequests.post(postUrl, data=json.dumps({
            "content": "Cont",
            "importance": 1,
            "authorName": "Szymon",
            "title": "Title!"
        }), headers = {'content-type': 'application/json'}),
    ]

    results = grequests.map(reqs, exception_handler=resolve_error)

    for result in results:
        assert(result.status_code == 200 or result.status_code == 204)

print('Running 1st test...')
test_timeout()
print('Test success!\n')

print('Running 2nd test...')
test_success_resolve()
print('Test success!\n')

