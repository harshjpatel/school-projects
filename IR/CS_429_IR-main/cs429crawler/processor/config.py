import os

LOG_LEVEL = 'DEBUG'
LOG_FORMAT = '%(asctime)s %(levelname)s : %(message)s'
APPLICATION_ROOT = '/cs429crawler'
FLASK_RUN_MODE = os.environ.get('MODE') or 'PROD'
WEB_PORT = os.environ.get('PORT') or 5000